package fr.croumy.bouge.presentation.background.broadcasts

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.services.NotificationService
import javax.inject.Inject

@AndroidEntryPoint
class StartupReceiver: BroadcastReceiver() {
    @Inject lateinit var notificationService: NotificationService

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            notificationService.showRebootNotification()
        }
    }
}

