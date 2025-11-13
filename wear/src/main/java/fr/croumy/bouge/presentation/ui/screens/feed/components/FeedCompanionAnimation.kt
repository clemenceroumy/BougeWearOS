package fr.croumy.bouge.presentation.ui.screens.feed.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.core.ui.components.AnimatedSprite

@Composable
fun FeedCompanionAnimation(
    companion: Companion,
    foodItem: FoodItem,
    onEnd: () -> Unit
) {
    val animatable = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        animatable.animateTo(0.01f)
    }
    val scale = animateFloatAsState(
        targetValue = animatable.value,
        animationSpec = keyframes {
            durationMillis = 1000
            1f at 0
            0.5f at 1000
            0.01f at 2000
        },
        label = "scale",
        finishedListener = { onEnd() }
    )

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

        Image(
            modifier = Modifier
                .size(Dimensions.largeIcon)
                .aspectRatio(1f)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    transformOrigin = TransformOrigin.Center
                },
            painter = painterResource(id = foodItem.assetId),
            contentDescription = null
        )
    }
}