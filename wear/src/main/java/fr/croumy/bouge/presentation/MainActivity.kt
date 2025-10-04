package fr.croumy.bouge.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavGraph
import fr.croumy.bouge.presentation.theme.BougeTheme
import fr.croumy.bouge.presentation.background.workers.DailyCheckWorker
import fr.croumy.bouge.presentation.background.workers.WorkerHelper
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var workerHelper: WorkerHelper

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        super.onCreate(savedInstanceState)

        WorkManager
            .getInstance(this.applicationContext)
            .enqueueUniquePeriodicWork(
                "send_reminder_periodic",
                ExistingPeriodicWorkPolicy.REPLACE,
                DailyCheckWorker.setupWork
            )

        workerHelper.launchHungrinessWorker(ExistingWorkPolicy.KEEP)

        setContent {
            CompositionLocalProvider(LocalNavController provides rememberSwipeDismissableNavController()) {
                BougeTheme {
                    NavGraph(
                        navController = LocalNavController.current,
                        navState = rememberSwipeDismissableNavHostState()
                    )
                }
            }
        }
    }
}