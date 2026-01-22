package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanionRepository @Inject constructor(
    private val database: AppDatabase
) {
    val companionDao = database.companionDao()

    fun getCurrentCompanion() = companionDao.getCurrentCompanion()

    suspend fun getDeadCompanions() = companionDao.getCompanionHistory()

    suspend fun insertCompanion(companionEntity: CompanionEntity) {
        companionDao.insertCompanion(companionEntity)
    }

    suspend fun updateCompanionBackground(backgroundUid: UUID) {
        companionDao.updateCompanionBackground(backgroundUid)
    }

    suspend fun updateCompanionStats(companionEntity: CompanionEntity) {
        companionDao.updateCompanionStats(companionEntity.happiness, companionEntity.hungriness, companionEntity.health)
    }

    suspend fun updateCompanionDeath(deathDate: ZonedDateTime) {
        companionDao.updateCompanionDeath(deathDate)
    }
}