package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.navigation.navigateAndPopUpTo
import fr.croumy.bouge.presentation.ui.screens.home.HomeScreen
import fr.croumy.bouge.presentation.ui.screens.menu.MenuScreen
import fr.croumy.bouge.presentation.ui.screens.stats.CompanionStatsScreen
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val homeScreens = listOf(NavRoutes.Stats, NavRoutes.Home, NavRoutes.Menu)
    val pagerState = rememberPagerState(1) { homeScreens.size }

    val companion = mainViewModel.companion.collectAsState()

    LaunchedEffect(companion.value) {
        if(companion.value == null) navController.navigateAndPopUpTo(NavRoutes.Start.route, NavRoutes.Start.route)
    }

    if(companion.value != null) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { index ->
            when (index) {
                0 -> CompanionStatsScreen(companion = companion.value!!)
                1 -> HomeScreen(companion = companion.value!!)
                2 -> MenuScreen()
            }
        }
    }
}