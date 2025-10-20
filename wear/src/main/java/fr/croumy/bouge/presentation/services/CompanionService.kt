package fr.croumy.bouge.presentation.services

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import fr.croumy.bouge.presentation.data.mappers.toCompanion
import fr.croumy.bouge.presentation.constants.Constants
import fr.croumy.bouge.presentation.constants.KeyStore
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.models.companion.CompanionType
import fr.croumy.bouge.presentation.models.companion.Stats
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.repositories.CompanionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanionService @Inject constructor(
    private val companionRepository: CompanionRepository,
    private val dataStore: DataStore<Preferences>
) {
    val myCompanion: Flow<Companion?> = companionRepository
        .getCurrentCompanion()
        .map {
            it?.toCompanion()
        }

    fun getLastestDeadCompanion(): Companion? {
        return companionRepository.getLatestDeadCompanion().toCompanion()
    }

    fun selectCompanion(companionType: CompanionType, customName: String) {
        val companionEntity = CompanionEntity(
            name = customName,
            type = companionType::class.java.simpleName,
            birthDate = ZonedDateTime.now(),
        )
        companionRepository.insertCompanion(companionEntity)
    }

    fun selectBackground(itemId: UUID) {
        companionRepository.updateCompanionBackground(itemId)
    }

    fun getStats(): Flow<Stats> {
        return companionRepository.getCurrentCompanion()
            .mapNotNull {
                if (it == null) null
                else {
                    val stats = Stats(
                        happiness = it.happiness,
                        hungriness = it.hungriness,
                        health = it.health
                    )

                    stats
                }
            }
    }

    private fun setStatValue(type: StatsUpdate, currentValue: Float): Float {
        return when (type) {
            is StatsUpdate.UP -> {
                if (currentValue == Constants.STAT_MAX) currentValue
                else currentValue + type.value
            }
            is StatsUpdate.DOWN -> {
                if(currentValue == 0f) currentValue
                else currentValue - type.value
            }
            else -> currentValue
        }
    }

    private fun updateStat(updatedCompanion: CompanionEntity) {
        companionRepository.updateCompanionStats(updatedCompanion)

        checkIsDead(updatedCompanion)
    }

    private fun checkIsDead(companion: CompanionEntity) {
        if (companion.health == 0f || companion.happiness == 0f || companion.hungriness == 0f) {
            CoroutineScope(Dispatchers.IO).launch {
                dataStore.edit { preferences -> preferences[KeyStore.COMPANION_DEATH_SEEN] = false }
                companionRepository.updateCompanionDeath(ZonedDateTime.now())
            }
        }
    }

    suspend fun updateHealthStat(type: StatsUpdate) {
        val companion = companionRepository.getCurrentCompanion().first()
        if (companion != null) {
            val updatedStat = setStatValue(type, companion.health)

            val updatedCompanion = companion.copy(health = updatedStat)
            updateStat(updatedCompanion)
        }
    }

    suspend fun updateHappinessStat(type: StatsUpdate) {
        val companion = companionRepository.getCurrentCompanion().first()
        if (companion != null) {
            val updatedStat = setStatValue(type, companion.happiness)

            val updatedCompanion = companion.copy(happiness = updatedStat)
            updateStat(updatedCompanion)
        }
    }

    suspend fun updateHungrinessStat(type: StatsUpdate) {
        val companion = companionRepository.getCurrentCompanion().first()
        if (companion != null) {
            val updatedStat = setStatValue(type, companion.hungriness)

            val updatedCompanion = companion.copy(hungriness = updatedStat)
            updateStat(updatedCompanion)
        }
    }
}