package fr.croumy.bouge.presentation.background.broadcasts

import android.Manifest
import android.R
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.services.HealthService
import fr.croumy.bouge.presentation.services.NotificationService

@AndroidEntryPoint
class StartupReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, HealthService::class.java)

            val persistentNotification = NotificationCompat.Builder(context, NotificationService.CHANNEL_ID_FOREGROUND)
                .setSmallIcon(R.drawable.ic_lock_power_off)
                .setContentTitle("Rebooting")
                .setContentText("Needs to reinitialize health data tracking")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(
                    NotificationCompat.Action(
                        R.drawable.ic_menu_rotate,
                        "Refresh",
                        PendingIntent.getForegroundService(context, 0, serviceIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
                    )
                )
                .build()

            NotificationManagerCompat.from(context).notify(NotificationService.REBOOT_NOTIFICATION_ID, persistentNotification)
        }
    }
}

