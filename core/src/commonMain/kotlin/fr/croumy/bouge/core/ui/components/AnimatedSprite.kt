package fr.croumy.bouge.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntSize
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
fun AnimatedSprite(
    modifier: Modifier = Modifier,
    imageId: DrawableResource,
    frameCount: Int,
    animationDuration: Int = 800
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sprite")

    val image = imageResource(imageId)

    val spriteWidth = image.width / frameCount
    val spriteHeight = image.height

    val animatedFrame = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = frameCount.toFloat(),
        animationSpec = infiniteRepeatable(
           animation = tween(animationDuration, easing = LinearEasing),
           repeatMode = RepeatMode.Restart
       ),
    )

    Canvas(modifier = modifier.aspectRatio(1f)) {
        val srcOffset = IntOffset(
            x = animatedFrame.value.toInt() * spriteWidth,
            y = 0
        )
        val srcSize = IntSize(spriteWidth, spriteHeight)
        val dstSize = this.size.toIntSize()

        drawImage(
            image = image,
            srcOffset = srcOffset,
            srcSize = srcSize,
            dstOffset = IntOffset.Zero,
            dstSize = dstSize
        )
    }
}