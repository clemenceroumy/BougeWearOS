package fr.croumy.bouge.presentation.ui.screens.shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.core.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.IconStatProgressBar

@Composable
fun ItemTooltip(
    item: FoodItem,
    onClose: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .clickable { onClose() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )

        Box(
            Modifier
                .fillMaxWidth(0.75f)
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(R.drawable.wood_tooltip),
                contentDescription = null,
                Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                Modifier.padding(horizontal = Dimensions.mediumPadding, vertical = Dimensions.smallPadding),
            ) {
                item.statsBoost.map {
                    IconStatProgressBar(
                        progress = it.value,
                        stat = it.key,
                        displayEmpty = false
                    )
                }
            }
        }
    }
}