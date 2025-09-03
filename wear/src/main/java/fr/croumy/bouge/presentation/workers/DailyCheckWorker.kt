package fr.croumy.bouge.presentation.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.croumy.bouge.presentation.models.companion.StatsType
import fr.croumy.bouge.presentation.services.CompanionService
import timber.log.Timber
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

@HiltWorker
class DailyCheckWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val companionService: CompanionService
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        //TEMP: GO DOWN EVERYDAY
        Timber.i("DailyCheckWorker executed")
        companionService.updateHealthStat(StatsType.DOWN(1))

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