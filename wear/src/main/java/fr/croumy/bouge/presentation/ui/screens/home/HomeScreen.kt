package fr.croumy.bouge.presentation.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val isWalking = viewModel.isWalking.collectAsState()
    val totalSteps = viewModel.totalSteps.collectAsState()
    val walks = viewModel.walks.collectAsState()
    val companion = viewModel.companion.collectAsState()

    if (companion.value != null) {
        Box(Modifier.fillMaxSize()) {
            BackgroundItem.fromId(companion.value!!.backgroundId)?.assetId?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                )
            }

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

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    companion.value!!.name,
                    style = MaterialTheme.typography.bodySmall
                )

                if (isWalking.value) {
                    AnimatedSprite(
                        Modifier.weight(1f),
                        imageId = companion.value!!.type.assetWalkingId,
                        frameCount = companion.value!!.type.assetWalkingFrame
                    )
                } else {
                    AnimatedSprite(
                        Modifier.weight(1f),
                        imageId = companion.value!!.type.assetIdleId,
                        frameCount = companion.value!!.type.assetIdleFrame
                    )
                }
            }
        }
    }
}