package fr.croumy.bouge

import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BougeWearOS",
    ) {
        Text("WINDOWS")
    }
}