package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import javax.inject.Inject

class WorkerHelper @Inject constructor(
    val context: Context
) {
    fun launchHungrinessWorker() {
        WorkManager
            .getInstance(context.applicationContext)
            .enqueueUniqueWork(
                "decrease_hungriness",
                ExistingWorkPolicy.REPLACE,
                HungrinessWorker.setupWork
            )
    }
}