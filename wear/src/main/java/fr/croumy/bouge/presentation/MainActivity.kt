package fr.croumy.bouge.presentation

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.google.android.horologist.compose.ambient.AmbientAware
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavGraph
import fr.croumy.bouge.presentation.services.HealthService
import fr.croumy.bouge.presentation.theme.BougeTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var healthService: HealthService

    companion object {
        lateinit var context: Context
    }

    // WHEN APP COME TO FOREGROUND, START CALLBACK
    override fun onResume() {
        super.onResume()
        healthService.initHealthCallback()
    }

    // WHEN APP GOES TO BACKGROUND, STOP CALLBACK AND START SERVICE
    override fun onStop() {
        super.onStop()
        healthService.initHealthService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        context = this

        installSplashScreen()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(LocalNavController provides rememberSwipeDismissableNavController()) {
                BougeTheme {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TimeText()

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