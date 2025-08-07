package fr.croumy.bouge

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(CrashReportingTree());
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