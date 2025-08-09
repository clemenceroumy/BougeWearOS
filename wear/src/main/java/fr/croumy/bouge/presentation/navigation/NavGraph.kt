package fr.croumy.bouge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.composable
import fr.croumy.bouge.presentation.ui.screens.HomeScreen
import fr.croumy.bouge.presentation.ui.screens.history.ExercisesHistoryScreen

@Composable
fun NavGraph(navController: NavHostController, navState: SwipeDismissableNavHostState) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen()
        }

        composable(NavRoutes.ExercisesHistory.route) {
            ExercisesHistoryScreen()
        }
    }
}