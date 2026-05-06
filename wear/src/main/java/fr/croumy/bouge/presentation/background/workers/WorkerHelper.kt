package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import timber.log.Timber
import javax.inject.Inject

class WorkerHelper @Inject constructor(
    val context: Context
) {
    fun launchHungrinessWorker(
        policy: ExistingWorkPolicy = ExistingWorkPolicy.REPLACE
    ) {
        Timber.tag("WorkerHelper").i("HungrinessWorker enqueued")
        WorkManager
            .getInstance(context.applicationContext)
            .enqueueUniqueWork(
                "decrease_hungriness",
                policy,
                HungrinessWorker.setupWork
            )
    }

    fun launchHappinessWorker(
        policy: ExistingWorkPolicy = ExistingWorkPolicy.REPLACE
    ) {
        Timber.tag("WorkerHelper").i("HappinessWorker enqueued")
        WorkManager
            .getInstance(context.applicationContext)
            .enqueueUniqueWork(
                "decrease_happiness",
                policy,
                HappinessWorker.setupWork
            )
    }

    fun launchDailyWorker() {
        Timber.tag("WorkerHelper").i("DailyCheckWorker enqueued")
        WorkManager
            .getInstance(context.applicationContext)
            .enqueueUniquePeriodicWork(
                "send_reminder_periodic",
                ExistingPeriodicWorkPolicy.KEEP,
                DailyCheckWorker.setupWork
            )
    }

    fun pauseCompanionStatsWorker() {
        WorkManager
            .getInstance(context.applicationContext)
            .cancelUniqueWork("decrease_hungriness")

        WorkManager
            .getInstance(context.applicationContext)
            .cancelUniqueWork("decrease_happiness")
    }

    fun resumeCompanionStatsWorker() {
        launchHungrinessWorker(ExistingWorkPolicy.KEEP)
        launchHappinessWorker(ExistingWorkPolicy.KEEP)
    }
}