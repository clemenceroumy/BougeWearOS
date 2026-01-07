package fr.croumy.bouge.presentation.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateAndPopUpTo(targetRoute: String, upToRoute: String) {
    this.navigate(targetRoute) {
        launchSingleTop = true
        popUpTo(upToRoute) { inclusive = true }
    }
}