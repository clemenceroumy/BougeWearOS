package fr.croumy.bouge

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.injection.appModule
import fr.croumy.bouge.ui.StartScreen
import fr.croumy.bouge.ui.tray.TrayComponent
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(appModule)
    }

    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.BottomEnd),
        size = DpSize(Window.WIDTH.dp, Window.HEIGHT.dp)
    )

    TrayComponent()

    Window(
        onCloseRequest = ::exitApplication,
        title = "BougeWearOS",
        state = windowState,
        transparent = true,
        undecorated = true,
        alwaysOnTop = true,
        resizable = false
    ) {
        WindowDraggableArea {
            StartScreen()
        }
    }
}