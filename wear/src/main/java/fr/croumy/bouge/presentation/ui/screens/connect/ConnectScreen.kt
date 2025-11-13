package fr.croumy.bouge.presentation.ui.screens.connect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ConnectScreen(
    viewModel: ConnectViewModel = hiltViewModel()
) {
    val companion = viewModel.companion.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (companion.value != null) {
            AnimatedSprite(
                modifier = Modifier.size(Dimensions.largeIcon),
                imageId = companion.value!!.type.assetIdleId,
                frameCount = companion.value!!.type.assetIdleFrame
            )
            ElevatedButton(
                onClick = { CoroutineScope(Dispatchers.IO).launch {
                    viewModel.connectToServer()
                } }
            ) { Text("Connect") }

            viewModel.connectionError.value?.let { Text(it) }
        } else {
            CircularProgressIndicator()
        }
    }
}
