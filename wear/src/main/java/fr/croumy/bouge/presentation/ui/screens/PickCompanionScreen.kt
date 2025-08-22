package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.presentation.models.CompanionType
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickCompanionScreen() {
    val companions = CompanionType.values
    val carouselState = rememberCarouselState { Int.MAX_VALUE / 2 }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowLeft,
            contentDescription = null,
            modifier = Modifier.size(Dimensions.smallIcon)
        )
        HorizontalUncontainedCarousel(
            state = carouselState,
            modifier = Modifier.weight(1f),
            flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(
                state = carouselState
            ),
            itemWidth = screenWidth - (Dimensions.smallIcon * 2),
        ) { index ->
            val itemIndex = index % companions.size
            val sprite = companions[itemIndex]

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(sprite.defaultName)
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    AnimatedSprite(
                        sprite.assetIdleId,
                        sprite.assetIdleFrame,
                    )
                }
                Spacer(Modifier.height(0.dp))
            }
        }
        Icon(
            Icons.AutoMirrored.Filled.ArrowRight,
            contentDescription = null,
            modifier = Modifier.size(Dimensions.smallIcon)
        )
    }
}