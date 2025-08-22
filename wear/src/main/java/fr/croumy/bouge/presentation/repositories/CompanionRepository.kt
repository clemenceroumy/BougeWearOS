package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanionRepository @Inject constructor(
    private val database: AppDatabase
) {
    val companionDao = database.companionDao()

    fun getCurrentCompanion() = companionDao.getCurrentCompanion()
}