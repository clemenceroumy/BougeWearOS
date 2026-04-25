package fr.croumy.bouge.ui.tray

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.rememberTrayState
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.menu_connect
import bouge.desktop.generated.resources.menu_disconnect
import bouge.desktop.generated.resources.menu_exit

import fr.croumy.bouge.services.BleScanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject


@Composable
fun ApplicationScope.TrayComponent(
    bleScanner: BleScanner = koinInject()
) {
    val trayState = rememberTrayState()
    val isConnected = bleScanner.isConnected.collectAsState()

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    val connectionLabel = stringResource(if (isConnected.value) Res.string.menu_disconnect else Res.string.menu_connect)
    val exitLabel = stringResource(Res.string.menu_exit)

    Tray(
        icon = TrayIcon,
        tooltip = "",
    ) {
        Item(
            connectionLabel,
            onClick = {
                if (isConnected.value) coroutineScope.launch { bleScanner.disconnect() }
                else bleScanner.scan()
            },
            //enabled = !bleScanner.isScanning.value
        )

        Item(
            exitLabel,
            onClick = {
                coroutineScope.launch {
                    exitApplication()
                }
            }
        )
    }

    /*val trayAppState = rememberTrayAppState(
        initialWindowSize = DpSize(300.dp, 420.dp),
        initiallyVisible = true // default is false
        // initialDismissMode defaults to TrayWindowDismissMode.AUTO
    )

    TrayApp(
        state = trayAppState,
        icon = TrayIcon,
        tooltip = "My Tray App",          // required

        // Optional visual controls (defaults shown below)
        transparent = true,               // default = true
        undecorated = true,               // default = true
        resizable = false,                // default = false
        windowsTitle = "My Tray Popup",   // default = "" — recommended (esp. on Linux & when undecorated=false)
        windowIcon = null,                // default = null — set your app icon; important on Linux & when undecorated=false

        menu = {                          // optional (default = null)
            Item("Toggle popup") { trayAppState.toggle() }
            Divider()
            Item("Quit") { exitApplication() }
        }
    ) {
            Text("Quick Settings")
    }*/
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}