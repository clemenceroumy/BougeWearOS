package fr.croumy.bouge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.composable
import fr.croumy.bouge.presentation.ui.historyCompanion.HistoryCompanionScreen
import fr.croumy.bouge.presentation.ui.screens.MainScreen
import fr.croumy.bouge.presentation.ui.screens.StartScreen
import fr.croumy.bouge.presentation.ui.screens.background.BackgroundScreen
import fr.croumy.bouge.presentation.ui.screens.connect.ConnectScreen
import fr.croumy.bouge.presentation.ui.screens.connect.ConnectViewModel
import fr.croumy.bouge.presentation.ui.screens.deadCompanion.DeadCompanionScreen
import fr.croumy.bouge.presentation.ui.screens.feed.FeedScreen
import fr.croumy.bouge.presentation.ui.screens.historyExercises.ExercisesHistoryScreen
import fr.croumy.bouge.presentation.ui.screens.pickCompanion.PickCompanionScreen
import fr.croumy.bouge.presentation.ui.screens.play.PlayScreen
import fr.croumy.bouge.presentation.ui.screens.shop.ShopScreen

@Composable
fun NavGraph(navController: NavHostController, navState: SwipeDismissableNavHostState) {
    val connectViewModel: ConnectViewModel = hiltViewModel() // Initialized here to persist across navigation

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavRoutes.Start.route,
        state = navState,
    ) {
        composable(NavRoutes.Start.route) {
            StartScreen()
        }

        composable(NavRoutes.PickCompanion.route) {
            PickCompanionScreen()
        }

        composable(NavRoutes.DeadCompanion.route) {
            DeadCompanionScreen()
        }

        composable(NavRoutes.Main.route) {
            MainScreen()
        }

        composable(NavRoutes.CompanionHistory.route) {
            HistoryCompanionScreen()
        }

        composable(NavRoutes.ExercisesHistory.route) {
            ExercisesHistoryScreen()
        }

        composable(NavRoutes.Shop.route) {
            ShopScreen()
        }

        composable(NavRoutes.Feed.route) {
            FeedScreen()
        }

        composable(NavRoutes.Play.route) {
            PlayScreen()
        }

        composable(NavRoutes.Background.route) {
            BackgroundScreen()
        }

        composable(NavRoutes.Connect.route) {
            ConnectScreen(
                viewModel = connectViewModel
            )
        }
    }
}