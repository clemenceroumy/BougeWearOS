package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.data.entities.DailyStepsEntity
import fr.croumy.bouge.presentation.extensions.toYYYYMMDD
import fr.croumy.bouge.presentation.repositories.DailyStepsRepository
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyStepsService @Inject constructor(
    private val dailyStepsRepository: DailyStepsRepository
) {
    fun insert(date: Instant, steps: Int) {
        dailyStepsRepository.insert(
            DailyStepsEntity(
                date = date.toYYYYMMDD(),
                totalSteps = steps
            )
        )
    }

    fun getTodaySteps(): Int {
        val today = Instant.now().toYYYYMMDD()
        return dailyStepsRepository.getByDate(today)?.totalSteps ?: 0
    }
}