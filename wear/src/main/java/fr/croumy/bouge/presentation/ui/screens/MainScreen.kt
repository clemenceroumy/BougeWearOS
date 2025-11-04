package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.ui.screens.home.HomeScreen
import fr.croumy.bouge.presentation.ui.screens.menu.MenuScreen
import fr.croumy.bouge.presentation.ui.screens.stats.CompanionStatsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {
    val homeScreens = listOf(NavRoutes.Stats, NavRoutes.Home, NavRoutes.Menu)
    val pagerState = rememberPagerState(1) { homeScreens.size }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { index ->
        when (index) {
            0 -> CompanionStatsScreen()
            1 -> HomeScreen()
            2 -> MenuScreen()
        }
    }
}