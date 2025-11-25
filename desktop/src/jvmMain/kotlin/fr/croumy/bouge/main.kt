package fr.croumy.bouge

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.ui.MainScreen

fun main() = application {
    val companion = BleScanner.currentCompanion

    Window(
        onCloseRequest = ::exitApplication,
        title = "BougeWearOS",
        transparent = true,
        undecorated = true
        //visible = isVisible.value
    ) {
        val state = rememberTrayState()

        val isConnected = BleScanner.isConnected.collectAsState()
        val peripherals = BleScanner.peripherals

        LaunchedEffect(BleScanner.isScanning.value) {
            println(BleScanner.isScanning.value)
        }

        Column {
            Tray(
                icon = TrayIcon,
                state = state,
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
                            it.toString(),
                            onClick = { BleScanner.selectPeripheral(it) }
                        )
                    }

                    Item(
                        "Exit",
                        onClick = ::exitApplication
                    )
                }
            )
        }

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