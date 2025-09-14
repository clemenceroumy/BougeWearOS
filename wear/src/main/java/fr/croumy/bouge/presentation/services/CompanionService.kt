package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import fr.croumy.bouge.presentation.data.mappers.toCompanion
import fr.croumy.bouge.presentation.constants.Constants
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.models.companion.CompanionType
import fr.croumy.bouge.presentation.models.companion.Stats
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.repositories.CompanionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanionService @Inject constructor(
    private val companionRepository: CompanionRepository,
) {
    val myCompanion: Flow<Companion?> = companionRepository
        .getCurrentCompanion()
        .map {
            it?.toCompanion()
        }

    fun selectCompanion(companionType: CompanionType, customName: String) {
        val companionEntity = CompanionEntity(
            name = customName,
            type = companionType::class.java.simpleName,
            birthDate = ZonedDateTime.now(),
        )
        companionRepository.insertCompanion(companionEntity)
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

    suspend fun updateHealthStat(type: StatsUpdate) {
        val companion = companionRepository.getCurrentCompanion().first()
        if (companion != null) {
            val updatedStat = when (type) {
                    is StatsUpdate.UP -> {
                        if (companion.health == Constants.STAT_MAX) companion.health
                        else companion.health + type.value
                    }
                    is StatsUpdate.DOWN -> {
                        if(companion.health == 0f) companion.health
                        else companion.health - type.value
                    }
                    else -> companion.health
                }

            val updatedCompanion = companion.copy(health = updatedStat)
            companionRepository.updateCompanionStats(updatedCompanion)
        }
    }
}