package fr.croumy.bouge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.injection.allModules
import fr.croumy.bouge.services.CompanionService
import fr.croumy.bouge.ui.main.MainScreen
import fr.croumy.bouge.ui.tray.TrayComponent
import fr.croumy.bouge.ui.tray.TrayMenuComponent
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(allModules)
    }

    val companionService: CompanionService = koinInject()

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
            val companion = companionService.currentCompanion

            Box(Modifier.fillMaxSize()) {
                if (companion.value != null) {
                    MainScreen(companion.value!!)
                }
            }
        }
    }
}