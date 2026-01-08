package fr.croumy.bouge.presentation.ui.screens.play

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.core.models.shop.toy.ToyItem
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel(),
    playItem: ToyItem = ToyItem.Ball
) {
    val navController = LocalNavController.current
    val screenHeight = LocalConfiguration.current.smallestScreenWidthDp

    val companion = viewModel.companion.value
    val ballY = remember { mutableFloatStateOf(0f) }
    val ballThrown = remember { mutableStateOf(false) }

    LaunchedEffect(ballY.floatValue) {
        if(ballY.floatValue < -30f && !ballThrown.value) {
            ballThrown.value = true
        }
    }

    LaunchedEffect(ballThrown.value) {
        if(ballThrown.value) {
            delay(1100) // Animation duration
            viewModel.play(playItem)
            navController.popBackStack()
        }
    }

    companion?.let {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            AnimatedSprite(
                modifier = Modifier.fillMaxSize(),
                imageId = companion.type.assetIdleId,
                frameCount = companion.type.assetIdleFrame,
            )

            AnimatedVisibility(
                visible = !ballThrown.value,
                enter = EnterTransition.None,
                exit = slideOutVertically(
                    animationSpec = tween(1000, easing = LinearEasing),
                    targetOffsetY = { fullHeight -> -screenHeight }
                ) + scaleOut(
                    animationSpec = tween(1000, 100),
                    targetScale = 0.2f
                ),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Image(
                    modifier = Modifier
                        .size(Dimensions.largeIcon)
                        .aspectRatio(1f)
                        .draggable(
                            orientation = Orientation.Vertical,
                            state = rememberDraggableState { delta ->
                                ballY.floatValue = delta
                            }
                        ),
                    painter = painterResource(playItem.assetId),
                    contentDescription = null
                )
            }
        }
    }
}