package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.croumy.bouge.presentation.constants.KeyStore
import fr.croumy.bouge.presentation.extensions.dataStore
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.services.PermissionService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartScreen(
    startViewModel: StartViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val locationPermissionState = rememberPermissionState(PermissionService.LOCATION)
    val activityRecognitionPermissionState = rememberPermissionState(PermissionService.ACTIVITY_RECOGNITION)
    val notificationPermissionState = rememberPermissionState(PermissionService.NOTIFICATIONS)

    val locationIsGranted = locationPermissionState.status.isGranted
    val activityRecognitionIsGranted = activityRecognitionPermissionState.status.isGranted
    val notificationIsGranted = notificationPermissionState.status.isGranted || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU

    val isLoading = startViewModel.isLoading.value
    val hasCompanion = startViewModel.hasCompanion.value
    val isDeadSeen = runBlocking { context.dataStore.data.map { preferences -> preferences[KeyStore.COMPANION_DEATH_SEEN] ?: false }.first() }

    LaunchedEffect(locationIsGranted, activityRecognitionIsGranted, notificationIsGranted, isLoading) {
        if(!activityRecognitionIsGranted) {
            activityRecognitionPermissionState.launchPermissionRequest()
        } else if(!locationIsGranted) {
            locationPermissionState.launchPermissionRequest()
        } else if(!notificationIsGranted) {
            notificationPermissionState.launchPermissionRequest()
        } else if(!isLoading) {
            startViewModel.initHealthService()

            when {
                hasCompanion -> navController.navigate(NavRoutes.HasCompanionScreens.route)
                !hasCompanion && !isDeadSeen -> navController.navigate(NavRoutes.DeadCompanion.route)
                else -> navController.navigate(NavRoutes.PickCompanion.route)
            }
        }
    }

    if(startViewModel.isLoading.value) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}