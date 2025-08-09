package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.ui.screens.main.MainScreen
import fr.croumy.bouge.presentation.ui.screens.redirect.RedirectScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val homeScreens = listOf(NavRoutes.Main, NavRoutes.Redirect)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { homeScreens.count() },
        modifier = Modifier.fillMaxSize(),
        itemWidth = screenWidth,
    ) { index ->
        when(index) {
            0 -> MainScreen()
            1 -> RedirectScreen()
        }
    }
}