package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.services.PermissionService
import fr.croumy.bouge.presentation.ui.screens.main.MainScreen
import fr.croumy.bouge.presentation.ui.screens.redirect.RedirectScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen() {
    val homeScreens = listOf(NavRoutes.Main, NavRoutes.Redirect)
    val carouselState = rememberCarouselState { homeScreens.count() }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    HorizontalUncontainedCarousel(
        state = carouselState,
        modifier = Modifier.fillMaxSize(),
        flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(
            state = carouselState
        ),
        itemWidth = screenWidth,
    ) { index ->
        when (index) {
            0 -> MainScreen()
            1 -> RedirectScreen()
        }
    }
}