package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.models.companion.CompanionType
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.ui.components.Button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickCompanionScreen(
    pickCompanionViewModel: PickCompanionViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val companions = CompanionType.values
    val selectedCompanion = remember { mutableStateOf(companions.first()) }
    val carouselState = rememberCarouselState { Int.MAX_VALUE / 2 }

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
                selectedCompanion.value = companions[itemIndex]

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(selectedCompanion.value.defaultName)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        AnimatedSprite(
                            selectedCompanion.value.assetIdleId,
                            selectedCompanion.value.assetIdleFrame,
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
            onClick = {
                pickCompanionViewModel.selectCompanion(selectedCompanion.value)
                navController.navigate(NavRoutes.Home.route)
            },
            label = "Confirm"
        )
    }
}