package fr.croumy.bouge.presentation.ui.screens.feed.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.R
import fr.croumy.bouge.core.mocks.companionMock
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import org.jetbrains.compose.resources.painterResource

@Composable
fun FeedCompanionAnimation(
    companion: Companion,
    foodItem: FoodItem,
    onEnd: () -> Unit
) {
    val animatable = remember { Animatable(1f) }
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

    LaunchedEffect(Unit) {
        animatable.animateTo(0.01f)
    }

    Box(
        Modifier.fillMaxSize(),
    ) {
        Image(
            painterResource(companion.background.assetId),
            contentDescription = "",
        )

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(Dimensions.mediumPadding))
            OutlinedText(text = stringResource(R.string.inventory_bon_appetite))
            Row(
                Modifier
                    .padding(bottom = Dimensions.spriteBottomPadding),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier.wrapContentSize()
                ) {
                    Image(
                        painterResource(R.drawable.chair),
                        contentDescription = stringResource(R.string.description_chair),
                    )

                    AnimatedSprite(
                        modifier = Modifier
                            .size(Dimensions.mediumIcon)
                            .offset(x = (Dimensions.xxsmallPadding), y = (-23).dp),
                        imageId = companion.type.assetIdleId,
                        frameCount = companion.type.assetIdleFrame,
                    )
                }

                Column {
                    Image(
                        modifier = Modifier
                            .size(Dimensions.mediumIcon)
                            .aspectRatio(1f)
                            .graphicsLayer {
                                scaleX = scale.value
                                scaleY = scale.value
                                transformOrigin = TransformOrigin.Center
                            },
                        painter = painterResource(foodItem.assetId),
                        contentDescription = null
                    )
                    Image(
                        painterResource(R.drawable.table),
                        contentDescription = stringResource(R.string.description_table),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedCompanionAnimationPreview() {
    FeedCompanionAnimation(
        companion = companionMock,
        foodItem = FoodItem.Bread,
        onEnd = {}
    )
}