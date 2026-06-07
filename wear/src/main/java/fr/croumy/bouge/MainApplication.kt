package fr.croumy.bouge

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import dagger.hilt.android.HiltAndroidApp
import fr.croumy.bouge.presentation.services.NotificationService
import io.sentry.Sentry
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var notificationService: NotificationService

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(CrashReportingTree());

        notificationService.initNotificationChannels()
    }

    private class CrashReportingTree() : Timber.Tree() {
        override fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable?
        ) {
            // Not needed anymore because gradle "sentry" task automatically send log
            /*when (priority) {
                Log.VERBOSE -> Sentry.logger().trace(message)
                Log.DEBUG -> Sentry.logger().debug(message)
                Log.INFO -> Sentry.logger().info(message)
                Log.WARN -> Sentry.logger().warn(message)
                Log.ERROR -> {
                    /*val crashlytics = Firebase.crashlytics
                    crashlytics.log(message)*/
                    Sentry.logger().error(message)
                }
                else -> Sentry.logger().info(message)
            }*/
        }
    }
}