package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import javax.inject.Inject

class WorkerHelper @Inject constructor(
    val context: Context
) {
    fun launchHungrinessWorker(
        policy: ExistingWorkPolicy = ExistingWorkPolicy.REPLACE
    ) {
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
        WorkManager
            .getInstance(context.applicationContext)
            .enqueueUniqueWork(
                "decrease_happiness",
                policy,
                HappinessWorker.setupWork
            )
    }

    fun launchDailyWorker() {
        WorkManager
            .getInstance(context.applicationContext)
            .enqueueUniquePeriodicWork(
                "send_reminder_periodic",
                ExistingPeriodicWorkPolicy.REPLACE,
                DailyCheckWorker.setupWork
            )
    }
}