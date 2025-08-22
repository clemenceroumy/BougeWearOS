package fr.croumy.bouge.presentation.navigation

open class NavRoutes(val route: String) {
    object Start: NavRoutes("Start")
    object PickCompanion: NavRoutes("PickCompanion")
    object Home: NavRoutes("Home")
    object Main: NavRoutes("Main")
    object Redirect: NavRoutes("Redirect")
    object ExercisesHistory: NavRoutes("ExercisesHistory")
}