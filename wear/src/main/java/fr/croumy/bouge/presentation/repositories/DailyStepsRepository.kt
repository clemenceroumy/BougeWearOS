package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.DailyStepsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyStepsRepository @Inject constructor(
    private val database: AppDatabase
) {
    val dailyStepsDao = database.dailyStepsDao()

    suspend fun getByDate(date: String) = dailyStepsDao.getByDate(date)
    suspend fun insert(steps: DailyStepsEntity) = dailyStepsDao.insert(steps)
}