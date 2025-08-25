package fr.croumy.bouge.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntSize


@Composable
fun AnimatedSprite(
    @DrawableRes imageId: Int,
    frameCount: Int,
    animationDuration: Int = 800
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sprite")

    val imageBitmap = ImageBitmap.imageResource(id = imageId)

    val spriteWidth = imageBitmap.width / frameCount
    val spriteHeight = imageBitmap.height

    val animatedFrame = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = frameCount.toFloat(),
        animationSpec = infiniteRepeatable(
           animation = tween(animationDuration, easing = LinearEasing),
           repeatMode = RepeatMode.Restart
       ),
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val srcOffset = IntOffset(
            x = animatedFrame.value.toInt() * spriteWidth,
            y = 0
        )
        val srcSize = IntSize(spriteWidth, spriteHeight)
        val dstSize = this.size.toIntSize()

        drawImage(
            image = imageBitmap,
            srcOffset = srcOffset,
            srcSize = srcSize,
            dstOffset = IntOffset.Zero,
            dstSize = dstSize
        )
    }
}