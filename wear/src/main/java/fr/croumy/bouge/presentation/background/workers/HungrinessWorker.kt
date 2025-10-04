package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.DailyStepsService
import fr.croumy.bouge.presentation.usecases.credits.RegisterWonCreditsUseCase
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

@HiltWorker
class HungrinessWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val companionService: CompanionService,
    val workerHelper: WorkerHelper
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        companionService.updateHungrinessStat(StatsUpdate.DOWN(0.5f))

        workerHelper.launchHungrinessWorker()

        return Result.success()
    }

    companion object {
        // ONLY ONE AT ONCE
        val setupWork = OneTimeWorkRequestBuilder<HungrinessWorker>()
            .setInitialDelay(6, TimeUnit.HOURS)
            .build()
    }
}