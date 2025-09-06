package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.models.companion.StatsType
import fr.croumy.bouge.presentation.models.credit.CreditRewardType
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.DailyStepsService
import fr.croumy.bouge.presentation.usecases.credits.RegisterWonCreditsParams
import fr.croumy.bouge.presentation.usecases.credits.RegisterWonCreditsUseCase
import java.time.Duration
import java.time.LocalDateTime
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
        val todaySteps = dailyStepsService.getTodaySteps()
        if(todaySteps < Constants.DAILY_STEPS_MIN_GOAL_TO_KEEP_HEALTH) companionService.updateHealthStat(StatsType.DOWN(1))

        registerWonCreditsUseCase(
            RegisterWonCreditsParams(
                value = todaySteps,
                creditRewardType = CreditRewardType.TOTAL_DAILY_STEPS
            )
        )

        return Result.success()
    }

    companion object {
        val setupWork = PeriodicWorkRequestBuilder<DailyCheckWorker>(
            24, TimeUnit.HOURS,
            15, TimeUnit.MINUTES
        )
            .setInitialDelay(Duration.between(LocalDateTime.now(), LocalDateTime.now().withHour(23).withMinute(59).withSecond(59)))
            .build()
    }
}