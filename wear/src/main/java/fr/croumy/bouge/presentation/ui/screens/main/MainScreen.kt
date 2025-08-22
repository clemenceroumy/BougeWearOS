package fr.croumy.bouge.presentation.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.models.CompanionType
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val isWalking = viewModel.isWalking.collectAsState()
    val totalSteps = viewModel.totalSteps.collectAsState()
    val walks = viewModel.walks.collectAsState()
    val companion = viewModel.companion.collectAsState()
    val sprite = CompanionType.Frog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.DirectionsWalk,
                contentDescription = "Walking Icon",
                Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = totalSteps.value.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Walk: ${walks.value}",
            style = MaterialTheme.typography.bodySmall
        )

        if (isWalking.value) {
            AnimatedSprite(
                imageId = sprite.assetWalkingId,
                frameCount = sprite.assetWalkingFrame
            )
        } else {
            AnimatedSprite(
                imageId = sprite.assetIdleId,
                frameCount = sprite.assetIdleFrame
            )
        }
    }
}