package fr.croumy.bouge

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.ui.MainScreen

fun main() = application {
    val companion = BleScanner.currentCompanion
    val trayState = rememberTrayState()
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.BottomEnd),
        size = DpSize(500.dp, 100.dp)
    )

    val isConnected = BleScanner.isConnected.collectAsState()
    val peripherals = BleScanner.peripherals

    Tray(
        icon = TrayIcon,
        state = trayState,
        tooltip = "",
        onAction = {},
        menu = {
            Item(
                "Connect",
                onClick = { BleScanner.scan() },
                enabled = !BleScanner.isScanning.value && !isConnected.value
            )
            peripherals.value.map {
                Item(
                    it.identifier.toString(),
                    onClick = { BleScanner.connectPeripheral(it) }
                )
            }

            Item(
                "Exit",
                onClick = ::exitApplication
            )
        }
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "BougeWearOS",
        state = windowState,
        transparent = true,
        undecorated = true,
        alwaysOnTop = true,
        resizable = false
        //visible = isVisible.value
    ) {
        if (companion.value != null) {
            MainScreen(companion.value!!)
        }
    }
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}