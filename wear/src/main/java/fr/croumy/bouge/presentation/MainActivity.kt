package fr.croumy.bouge.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavGraph
import fr.croumy.bouge.presentation.services.HealthService
import fr.croumy.bouge.presentation.services.PermissionService
import fr.croumy.bouge.presentation.theme.BougeTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var healthService: HealthService

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        super.onCreate(savedInstanceState)

        setContent {
            val locationPermissionState = rememberPermissionState(PermissionService.LOCATION)
            val activityRecognitionPermissionState = rememberPermissionState(PermissionService.ACTIVITY_RECOGNITION)

            val locationIsGranted = locationPermissionState.status.isGranted
            val activityRecognitionIsGranted = activityRecognitionPermissionState.status.isGranted

            LaunchedEffect(locationIsGranted, activityRecognitionIsGranted) {
                if(!activityRecognitionIsGranted) {
                    activityRecognitionPermissionState.launchPermissionRequest()
                } else if(!locationIsGranted) {
                    locationPermissionState.launchPermissionRequest()
                } else {
                    healthService.initService()
                }
            }

            CompositionLocalProvider(LocalNavController provides rememberSwipeDismissableNavController()) {
                BougeTheme {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TimeText()

                        if(locationIsGranted && activityRecognitionIsGranted) {
                            NavGraph(
                                navController = LocalNavController.current,
                                navState = rememberSwipeDismissableNavHostState()
                            )
                        }
                    }
                }
            }
        }
    }
}