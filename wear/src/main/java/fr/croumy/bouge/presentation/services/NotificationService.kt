package fr.croumy.bouge.presentation.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import fr.croumy.bouge.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationService @Inject constructor(
    private val context: Context,
    val permissionService: PermissionService
) {
    companion object {
        val CHANNEL_ID_FOREGROUND = "CHANNEL_ID_FOREGROUND"
        val CHANNEL_ID_COMPANION = "CHANNEL_ID_COMPANION"
        val CHANNEL_NAME_FOREGROUND = "Foreground"
        val CHANNEL_NAME_COMPANION = "Companion"
        val REBOOT_NOTIFICATION_ID = 0
        val NOTIFICATION_FOREGROUND_HEALTH_SERVICE_ID = 1
        val NOTIFICATION_DEAD_COMPANION_ID = 2
    }

    fun initNotificationChannels() {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channels = listOf(
            NotificationChannel(CHANNEL_ID_FOREGROUND, CHANNEL_NAME_FOREGROUND, NotificationManager.IMPORTANCE_DEFAULT),
            NotificationChannel(CHANNEL_ID_COMPANION, CHANNEL_NAME_COMPANION, NotificationManager.IMPORTANCE_DEFAULT)
        )

        channels.forEach {
            notificationManager.createNotificationChannel(it)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showRebootNotification() {
        val serviceIntent = Intent(context, HealthService::class.java)

        val rebootNotification = NotificationCompat.Builder(context, CHANNEL_ID_FOREGROUND)
            .setSmallIcon(R.drawable.splash_icon)
            .setContentTitle(context.getString(R.string.notification_reboot_title))
            .setContentText(context.getString(R.string.notification_reboot_description))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(
                NotificationCompat.Action(
                    R.drawable.round_refresh,
                    context.getString(R.string.common_refresh),
                    PendingIntent.getForegroundService(context, 0, serviceIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
                )
            )
            .build()

         NotificationManagerCompat.from(context).notify(REBOOT_NOTIFICATION_ID, rebootNotification)
    }

    fun hideRebootNotification() {
        NotificationManagerCompat.from(context).cancel(REBOOT_NOTIFICATION_ID)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun sendDeadNotification() {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_COMPANION)
            .setSmallIcon(R.drawable.splash_icon)
            .setContentText(context.getString(R.string.companion_dead_desc))
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_DEAD_COMPANION_ID, notification)
    }
}