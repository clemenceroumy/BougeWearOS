package fr.croumy.bouge.presentation.navigation

open class NavRoutes(val route: String) {
    object Home: NavRoutes("Home")
    object Main: NavRoutes("Main")
    object Redirect: NavRoutes("Redirect")
    object ExercisesHistory: NavRoutes("ExercisesHistory")
}