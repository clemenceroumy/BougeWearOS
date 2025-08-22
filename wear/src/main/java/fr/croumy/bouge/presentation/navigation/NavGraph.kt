package fr.croumy.bouge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.composable
import fr.croumy.bouge.presentation.ui.screens.HomeScreen
import fr.croumy.bouge.presentation.ui.screens.StartScreen
import fr.croumy.bouge.presentation.ui.screens.history.ExercisesHistoryScreen
import fr.croumy.bouge.presentation.ui.screens.main.PickCompanionScreen

@Composable
fun NavGraph(navController: NavHostController, navState: SwipeDismissableNavHostState) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavRoutes.Start.route
    ) {
        composable(NavRoutes.Start.route) {
            StartScreen()
        }

        composable(NavRoutes.PickCompanion.route) {
            PickCompanionScreen()
        }

        composable(NavRoutes.Home.route) {
            HomeScreen()
        }

        composable(NavRoutes.ExercisesHistory.route) {
            ExercisesHistoryScreen()
        }
    }
}