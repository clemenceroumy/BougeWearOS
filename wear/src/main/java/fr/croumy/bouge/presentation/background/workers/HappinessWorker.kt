package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.services.CompanionService
import java.util.concurrent.TimeUnit

@HiltWorker
class HappinessWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val companionService: CompanionService,
    val workerHelper: WorkerHelper
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        companionService.updateHappinessStat(StatsUpdate.DOWN(1f))

        workerHelper.launchHappinessWorker()

        return Result.success()
    }

    companion object {
        // ONLY ONE AT ONCE
        val setupWork = OneTimeWorkRequestBuilder<HappinessWorker>()
            .setInitialDelay(24, TimeUnit.HOURS)
            .build()
    }
}