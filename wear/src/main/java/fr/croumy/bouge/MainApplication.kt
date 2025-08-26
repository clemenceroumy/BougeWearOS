package fr.croumy.bouge

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import dagger.hilt.android.HiltAndroidApp
import fr.croumy.bouge.presentation.services.NotificationService
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject lateinit var notificationService: NotificationService

    override fun onCreate() {
        super.onCreate()

        Timber.plant(CrashReportingTree());

        notificationService.initNotificationChannel()
    }

    private class CrashReportingTree: Timber.Tree() {
        override fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable?
        ) {
            val crashlytics = Firebase.crashlytics

            crashlytics.log(message)
        }
    }
}