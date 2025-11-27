package fr.croumy.bouge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.models.Direction
import kotlinx.coroutines.delay

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

            moveValue.value = when(direction.value){
                Direction.LEFT -> moveValue.value - 10f
                Direction.RIGHT -> moveValue.value + 10f
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedSprite(
            modifier = Modifier
                .size(70.dp)
                .graphicsLayer {
                    rotationY = when(direction.value){
                        Direction.LEFT -> 180f
                        Direction.RIGHT -> 0f
                    }
                    translationX = moveValue.value
                },
            imageId = companion.type.assetIdleId,
            frameCount = companion.type.assetIdleFrame,
        )
    }
}