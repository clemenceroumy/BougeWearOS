package fr.croumy.bouge.presentation

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.services.CompanionService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var workerHelper: WorkerHelper

    @Inject
    lateinit var companionService: CompanionService

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        super.onCreate(savedInstanceState)

        workerHelper.launchDailyWorker()
        workerHelper.launchHungrinessWorker(ExistingWorkPolicy.KEEP)

        setContent {
            val navigator = rememberSwipeDismissableNavController()
            val companion = companionService.myCompanion.collectAsState(initial = null)
            val companionLoaded = remember { mutableStateOf(false) }

            // LISTEN TO COMPANION GLOBALLY TO REDIRECT TO START IF IT DIES
            LaunchedEffect(companion.value) {
                if(companion.value != null) companionLoaded.value = true

                if(companion.value == null && companionLoaded.value) {
                    navigator.popBackStack(
                        route = NavRoutes.Start.route,
                        inclusive = false,
                    )
                }
            }

            CompositionLocalProvider(LocalNavController provides navigator) {
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