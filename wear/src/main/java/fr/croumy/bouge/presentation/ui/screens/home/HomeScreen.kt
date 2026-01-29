package fr.croumy.bouge.presentation.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.OutlinedText
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
        Box(
            Modifier.fillMaxSize()
        ) {
            BackgroundItem.fromId(companion.value!!.backgroundId)?.assetId?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimensions.mediumPadding)
                        .padding(horizontal = Dimensions.smallPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(R.drawable.icon_steps),
                            contentDescription = stringResource(R.string.description_icon_walk),
                        )
                        Spacer(Modifier.size(Dimensions.xsmallPadding))
                        OutlinedText(
                            text = totalSteps.value.toString(),
                            style = MaterialTheme.typography.displayMedium
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(R.drawable.icon_walk),
                            contentDescription = stringResource(R.string.description_icon_walk)
                        )
                        Spacer(Modifier.size(Dimensions.xsmallPadding))
                        OutlinedText(
                            text = "${walks.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    OutlinedText(
                        text = companion.value!!.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(Dimensions.smallPadding))

                    val spriteModifier = Modifier.size(Dimensions.largeIcon)

                    if (isWalking.value) {
                        AnimatedSprite(
                            spriteModifier,
                            imageId = companion.value!!.type.assetWalkingId,
                            frameCount = companion.value!!.type.assetWalkingFrame
                        )
                    } else {
                        AnimatedSprite(
                            spriteModifier,
                            imageId = companion.value!!.type.assetIdleId,
                            frameCount = companion.value!!.type.assetIdleFrame
                        )
                    }

                    Spacer(Modifier.height(Dimensions.spriteBottomPadding))
                }
            }
        }
    }
}