package fr.croumy.bouge

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import dagger.hilt.android.HiltAndroidApp
import fr.croumy.bouge.presentation.services.LogService
import fr.croumy.bouge.presentation.services.NotificationService
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var notificationService: NotificationService

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var logService: LogService

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(CrashReportingTree(logService));

        notificationService.initNotificationChannels()
    }

    private class CrashReportingTree(val logService: LogService) : Timber.Tree() {
        override fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable?
        ) {
            val priorityString = when (priority) {
                Log.VERBOSE -> "VERBOSE"
                Log.DEBUG -> "DEBUG"
                Log.INFO -> "INFO"
                Log.WARN -> "WARN"
                Log.ERROR -> "ERROR"
                Log.ASSERT -> "ASSERT"
                else -> "UNKNOWN"
            }

            try {
                logService.registerLog("[$priorityString] $tag: $message")
            } catch (e: Exception) {
                val crashlytics = Firebase.crashlytics
                crashlytics.log("[$priorityString] $tag: $message")
            }

            if (priority == Log.ERROR) {
                val crashlytics = Firebase.crashlytics
                crashlytics.log(message)
            }
        }
    }
}