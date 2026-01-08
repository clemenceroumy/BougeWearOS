package fr.croumy.bouge.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.constants.Constants
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.helpers.grass
import fr.croumy.bouge.models.Direction
import fr.croumy.bouge.theme.Dimensions
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    companion: Companion,
    viewModel: MainViewModel = koinInject()
) {
    val lastDrop = viewModel.currentDrops.value.lastOrNull()

    val isDropVisible = remember { mutableStateOf(false) }

    LaunchedEffect(lastDrop) {
        if (lastDrop != null) {
            isDropVisible.value = true
            delay(Constants.EnterAnimationDuration.toLong())
            isDropVisible.value = false
        }
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedSprite(
            modifier = Modifier
                .size(Window.HEIGHT.dp)
                .graphicsLayer {
                    rotationY = when (viewModel.direction.value) {
                        Direction.LEFT -> 180f
                        Direction.RIGHT -> 0f
                    }
                    translationX = viewModel.moveValue.value
                },
            imageId = companion.type.assetIdleId,
            frameCount = companion.type.assetIdleFrame,
        )

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxHeight(1 / 2f)
                .align(Alignment.TopCenter),
            visible = isDropVisible.value,
            enter = slideInVertically(tween(Constants.EnterAnimationDuration)) { fullHeight -> fullHeight / 2 } + fadeIn(tween(200)),
            exit = slideOutVertically(tween(Constants.ExitAnimationDuration)) + fadeOut(),
        ) {
            Image(
                painter = painterResource(lastDrop!!.assetId),
                contentDescription = null,
                modifier = Modifier.size(Dimensions.mediumIcon),
                contentScale = ContentScale.FillWidth
            )
        }

        Row(
            Modifier.wrapContentWidth()
        ) {
            grass.map {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.mediumIcon),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}