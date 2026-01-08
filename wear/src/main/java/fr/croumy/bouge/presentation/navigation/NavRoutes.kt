package fr.croumy.bouge.presentation.navigation

open class NavRoutes(val route: String) {
    object Start: NavRoutes("Start")
    object PickCompanion: NavRoutes("PickCompanion")
    object DeadCompanion: NavRoutes("DeadCompanion")
    object Main: NavRoutes("Main")
    object Stats: NavRoutes("Stats")
    object Home: NavRoutes("Home")
    object Menu: NavRoutes("Menu")
    object CompanionHistory: NavRoutes("CompanionHistory")
    object ExercisesHistory: NavRoutes("ExercisesHistory")
    object Shop: NavRoutes("Shop")
    object Feed: NavRoutes("Feed")
    object Play: NavRoutes("Play")
    object Background: NavRoutes("Background")
    object Connect: NavRoutes("Connect")
}