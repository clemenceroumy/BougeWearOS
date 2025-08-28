package fr.croumy.bouge.presentation.navigation

open class NavRoutes(val route: String) {
    object Start: NavRoutes("Start")
    object PickCompanion: NavRoutes("PickCompanion")
    object Main: NavRoutes("Main")
    object Stats: NavRoutes("Stats")
    object Home: NavRoutes("Home")
    object Menu: NavRoutes("Menu")
    object ExercisesHistory: NavRoutes("ExercisesHistory")
}