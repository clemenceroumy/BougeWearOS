package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.services.PermissionService

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartScreen(
    startViewModel: StartViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val locationPermissionState = rememberPermissionState(PermissionService.LOCATION)
    val activityRecognitionPermissionState = rememberPermissionState(PermissionService.ACTIVITY_RECOGNITION)
    val notificationPermissionState = rememberPermissionState(PermissionService.NOTIFICATIONS)

    val locationIsGranted = locationPermissionState.status.isGranted
    val activityRecognitionIsGranted = activityRecognitionPermissionState.status.isGranted
    val notificationIsGranted = notificationPermissionState.status.isGranted || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU

    val isLoading = startViewModel.isLoading.value
    val hasCompanion = startViewModel.hasCompanion.value

    LaunchedEffect(locationIsGranted, activityRecognitionIsGranted, notificationIsGranted, isLoading) {
        if(!activityRecognitionIsGranted) {
            activityRecognitionPermissionState.launchPermissionRequest()
        } else if(!locationIsGranted) {
            locationPermissionState.launchPermissionRequest()
        } else if(!notificationIsGranted) {
            notificationPermissionState.launchPermissionRequest()
        } else if(!isLoading) {
            startViewModel.initHealthService()

            navController.navigate(
                if(hasCompanion) NavRoutes.Main.route else NavRoutes.PickCompanion.route
            )
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