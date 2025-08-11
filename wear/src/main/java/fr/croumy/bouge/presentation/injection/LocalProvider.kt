package fr.croumy.bouge.presentation.injection

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import fr.croumy.bouge.presentation.MainActivity

val LocalNavController = compositionLocalOf { NavHostController(MainActivity.context) }