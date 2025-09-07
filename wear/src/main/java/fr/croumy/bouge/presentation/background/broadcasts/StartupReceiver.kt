package fr.croumy.bouge.presentation.background.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.background.workers.RegisterForPassiveDataWorker

@AndroidEntryPoint
class StartupReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        if(intent.action == Intent.ACTION_BOOT_COMPLETED) {
            WorkManager
                .getInstance(context)
                .enqueueUniqueWork(
                    "register_for_passive_data_work",
                    androidx.work.ExistingWorkPolicy.REPLACE,
                    RegisterForPassiveDataWorker.setupWork
                )
        }
    }
}

