package fr.croumy.bouge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.composable
import fr.croumy.bouge.presentation.ui.screens.ExerciseScreen

@Composable
fun NavGraph(navController: NavHostController, navState: SwipeDismissableNavHostState) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavRoutes.Exercise.route
    ) {
        composable(NavRoutes.Exercise.route) {
            ExerciseScreen()
        }
    }
}