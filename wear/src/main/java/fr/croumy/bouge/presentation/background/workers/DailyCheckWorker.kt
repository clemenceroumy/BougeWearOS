package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
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

        Timber.tag("DailyCheckWorker").i("DailyCheckWorker is running: todaySteps = $todaySteps")
        if (todaySteps < Constants.DAILY_STEPS_MIN_GOAL_TO_KEEP_HEALTH) companionService.updateHealthStat(StatsUpdate.DOWN(1f))

        registerWonCreditsUseCase(
            RegisterWonCreditsParams(
                value = todaySteps,
                creditRewardType = CreditRewardType.TOTAL_DAILY_STEPS
            )
        )

        return Result.success()
    }

    companion object {
        val setupWork = OneTimeWorkRequestBuilder<DailyCheckWorker>()
            .build()
    }
}