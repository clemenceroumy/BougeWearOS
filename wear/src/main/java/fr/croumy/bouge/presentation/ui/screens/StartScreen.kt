package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    val locationIsGranted = locationPermissionState.status.isGranted
    val activityRecognitionIsGranted = activityRecognitionPermissionState.status.isGranted


    LaunchedEffect(locationIsGranted, activityRecognitionIsGranted) {
        if(!activityRecognitionIsGranted) {
            activityRecognitionPermissionState.launchPermissionRequest()
        } else if(!locationIsGranted) {
            locationPermissionState.launchPermissionRequest()
        } else {
            startViewModel.initHealthService()

            navController.navigate(
                if(startViewModel.hasCompanion) NavRoutes.Home.route else NavRoutes.PickCompanion.route
            )
        }
    }
}