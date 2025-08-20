package fr.croumy.bouge.presentation.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationService @Inject constructor(
    private val context: Context
) {
    companion object {
        val CHANNEL_ID_FOREGROUND = "CHANNEL_ID_FOREGROUND_WALKS"
        val CHANNEL_NAME_FOREGROUND = "ForegroundWalks"
    }

    fun initNotificationChannel() {
        val name = CHANNEL_NAME_FOREGROUND
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID_FOREGROUND, name, importance)
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}