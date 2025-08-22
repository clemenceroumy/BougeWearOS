package fr.croumy.bouge.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.presentation.models.CompanionType
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.ui.components.Button
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickCompanionScreen() {
    val companions = CompanionType.values
    val carouselState = rememberCarouselState { Int.MAX_VALUE / 2 }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
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
                flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(state = carouselState),
                itemWidth = screenWidth - (Dimensions.smallIcon * 2)
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
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        AnimatedSprite(
                            sprite.assetIdleId,
                            sprite.assetIdleFrame,
                        )
                    }
                }
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = null,
                modifier = Modifier.size(Dimensions.smallIcon)
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            label = "Confirm"
        )
    }
}