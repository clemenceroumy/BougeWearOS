package fr.croumy.bouge.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.grass_0
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.helpers.grass
import fr.croumy.bouge.helpers.grassAssetSize
import fr.croumy.bouge.models.Direction
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(
    companion: Companion
) {
    val direction = mutableStateOf(Direction.RIGHT)
    val moveValue = remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(800L) // ANIMATION SPEED
            direction.value = Direction.random()

            moveValue.value = when (direction.value) {
                Direction.LEFT -> moveValue.value - 10f
                Direction.RIGHT -> moveValue.value + 10f
            }
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
                    rotationY = when (direction.value) {
                        Direction.LEFT -> 180f
                        Direction.RIGHT -> 0f
                    }
                    translationX = moveValue.value
                },
            imageId = companion.type.assetIdleId,
            frameCount = companion.type.assetIdleFrame,
        )

        Row(
            Modifier
                .wrapContentWidth()
        ) {
            grass.map {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(grassAssetSize.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}