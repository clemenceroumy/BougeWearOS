package fr.croumy.bouge.presentation.ui.screens.connect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.theme.Dimensions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ConnectScreen(
    viewModel: ConnectViewModel
) {
    val navController = LocalNavController.current

    val companion = viewModel.companion.collectAsState()
    val isAdvertising = viewModel.isAdvertising.collectAsState()
    val isConnected = viewModel.isConnected.collectAsState()
    val isSent = viewModel.isSent.collectAsState()

    val hasBeenSent = remember { mutableStateOf(false) }

    LaunchedEffect(isConnected.value) {
        if(isConnected.value) {
            hasBeenSent.value = true

            // ON CONNECTED, REMOVE BACKSTACK TO MAKE APP UNUSABLE
            navController.navigate(NavRoutes.Connect.route) {
                launchSingleTop = true
                popUpTo(NavRoutes.Start.route) { inclusive = true }
            }
        }

        if(!isConnected.value && hasBeenSent.value) {
            // GET NAVIGATION HISTORY BACK ON RETRIEVING COMPANION
            navController.navigate(NavRoutes.Start.route) {
                launchSingleTop = true
            }
            viewModel.resumeCompanionStatsWorker()
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (companion.value != null) {
            if (companion.value!!.available) AnimatedSprite(
                modifier = Modifier.size(Dimensions.largeIcon),
                imageId = companion.value!!.type.assetIdleId,
                frameCount = companion.value!!.type.assetIdleFrame
            ) else Text("AWAY...")

            ElevatedButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.connectToServer()
                    }
                },
                enabled = companion.value!!.available && !isAdvertising.value && !isConnected.value && !isSent.value
            ) {
                Text(
                    when {
                        !isAdvertising.value && !isConnected.value && !isSent.value -> "Connect"
                        isAdvertising.value && !isConnected.value && !isSent.value -> "Connecting..."
                        !isAdvertising.value && isConnected.value && !isSent.value -> "Sending..."
                        !isAdvertising.value && isConnected.value && isSent.value -> "Done!"
                        else -> ""
                    }
                )
            }
        } else {
            CircularProgressIndicator()
        }
    }
}
