package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.ui.screens.main.MainScreen
import fr.croumy.bouge.presentation.ui.screens.menu.RedirectScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen() {
    val homeScreens = listOf(NavRoutes.Main, NavRoutes.Redirect)
    val pagerState = rememberPagerState { homeScreens.size }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { index ->
        when (index) {
            0 -> MainScreen()
            1 -> RedirectScreen()
        }
    }
}