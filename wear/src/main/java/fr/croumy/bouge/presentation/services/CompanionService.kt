package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import fr.croumy.bouge.presentation.models.CompanionType
import fr.croumy.bouge.presentation.repositories.CompanionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanionService @Inject constructor(
    private val companionRepository: CompanionRepository
) {
    val myCompanion: StateFlow<CompanionEntity?> = companionRepository.getCurrentCompanion().stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun selectCompanion(companionType: CompanionType) {
        val companionEntity = CompanionEntity(
            name = companionType.defaultName,
            type = companionType::class.java.simpleName,
            birthDate = ZonedDateTime.now(),
        )
        companionRepository.insertCompanion(companionEntity)
    }
}