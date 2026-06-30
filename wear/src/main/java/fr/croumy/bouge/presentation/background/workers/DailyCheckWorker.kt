package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.croumy.bouge.presentation.constants.Constants
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.models.credit.CreditRewardType
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.DailyStepsService
import fr.croumy.bouge.presentation.usecases.credits.RegisterWonCreditsParams
import fr.croumy.bouge.presentation.usecases.credits.RegisterWonCreditsUseCase
import timber.log.Timber
import java.net.UnknownHostException
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

@HiltWorker
class DailyCheckWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val companionService: CompanionService,
    val dailyStepsService: DailyStepsService,
    val registerWonCreditsUseCase: RegisterWonCreditsUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val currentDate = LocalDateTime.now()
            Timber.tag("DailyCheckWorker").i("Running datetime: $currentDate")

            val dailyDate = if (currentDate.toLocalTime().isBefore(LocalTime.of(TRIGGER_HOUR, TRIGGER_MINUTE))) {
                currentDate.minusDays(1)
            } else {
                currentDate
            }
            val dailyInstant = dailyDate.atZone(ZoneId.systemDefault()).toInstant()

            Timber.tag("DailyCheckWorker").i("Checking steps for date: $dailyDate")

            val todaySteps = dailyStepsService.getStepsByDate(dailyInstant)

            Timber.tag("DailyCheckWorker").i("DailyCheckWorker is running: todaySteps = $todaySteps")
            if (todaySteps < Constants.DAILY_STEPS_MIN_GOAL_TO_KEEP_HEALTH) companionService.updateHealthStat(StatsUpdate.DOWN(1f))

            registerWonCreditsUseCase(
                RegisterWonCreditsParams(
                    value = todaySteps,
                    creditRewardType = CreditRewardType.TOTAL_DAILY_STEPS
                )
            )

            return Result.success()
        } catch (e: UnknownHostException) {
            Result.retry()
        } catch (e: Exception) {
            Timber.tag("DailyCheckWorker").e(e, "Error while running DailyCheckWorker")
            Result.failure()
        }
    }

    companion object {
        const val TRIGGER_HOUR = 23
        const val TRIGGER_MINUTE = 45

        val setupWork = PeriodicWorkRequestBuilder<DailyCheckWorker>(
            24, TimeUnit.HOURS,
            15, TimeUnit.MINUTES
        )
            .setInitialDelay(Duration.between(LocalDateTime.now(), LocalDateTime.now().withHour(TRIGGER_HOUR).withMinute(TRIGGER_MINUTE).withSecond(0)))
            .build()
    }
}