package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import fr.croumy.bouge.presentation.data.mappers.toCompanion
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.models.companion.CompanionType
import fr.croumy.bouge.presentation.repositories.CompanionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanionService @Inject constructor(
    private val companionRepository: CompanionRepository
) {
    val myCompanion: StateFlow<Companion?> = getMyCompanion().stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun getMyCompanion(): Flow<Companion?> {
        return companionRepository.getCurrentCompanion().transform {
            emit(it?.toCompanion())
        }
    }

    fun selectCompanion(companionType: CompanionType) {
        val companionEntity = CompanionEntity(
            name = companionType.defaultName,
            type = companionType::class.java.simpleName,
            birthDate = ZonedDateTime.now(),
        )
        companionRepository.insertCompanion(companionEntity)
    }
}