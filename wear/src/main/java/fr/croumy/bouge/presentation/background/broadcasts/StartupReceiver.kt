package fr.croumy.bouge.presentation.background.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import fr.croumy.bouge.presentation.background.workers.RegisterForPassiveDataWorker

class StartupReceiver : BroadcastReceiver() {

   override fun onReceive(context: Context, intent: Intent) {
       if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

       WorkManager.getInstance(context).enqueue(
           OneTimeWorkRequestBuilder<RegisterForPassiveDataWorker>().build()
       )
   }
}

