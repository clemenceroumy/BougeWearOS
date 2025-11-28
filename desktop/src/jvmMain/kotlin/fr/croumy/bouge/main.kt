package fr.croumy.bouge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    Tray(
        icon = TrayIcon,
        state = trayState,
        tooltip = "",
        onAction = {},
        menu = {
            Item(
                if(isConnected.value) "Disconnect" else "Connect",
                onClick = {
                    if(isConnected.value) coroutineScope.launch { BleScanner.writeCompanion() }
                    else BleScanner.scan()
                },
                enabled = !BleScanner.isScanning.value
            )

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
        Box(Modifier.fillMaxSize()) {
            if (companion.value != null) {
                MainScreen(companion.value!!)
            } else if (BleScanner.isScanning.value) {
                Column(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(5.dp)
                ) {
                    if(peripherals.value.isEmpty()){
                        CircularProgressIndicator(
                            Modifier.size(30.dp)
                        )
                    } else peripherals.value.map {
                        TextButton(onClick = { BleScanner.connectPeripheral(it) }) {
                            Text(
                                it.identifier.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}