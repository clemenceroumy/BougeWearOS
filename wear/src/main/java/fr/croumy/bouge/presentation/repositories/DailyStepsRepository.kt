package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.DailyStepsEntity
import javax.inject.Inject

class DailyStepsRepository @Inject constructor(
    private val database: AppDatabase
) {
    val dailyStepsDao = database.dailyStepsDao()

    fun getAll() = dailyStepsDao.getAll()
    fun getByDate(date: String) = dailyStepsDao.getByDate(date)
    fun insert(steps: DailyStepsEntity) = dailyStepsDao.insert(steps)
}