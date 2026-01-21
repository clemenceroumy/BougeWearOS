package fr.croumy.bouge.ui.tray

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
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

    Tray(
        icon = TrayIcon,
        state = trayState,
        tooltip = "",
        onAction = {},
        menu = {
            Item(
                stringResource(if (isConnected.value) Res.string.menu_disconnect else Res.string.menu_connect),
                onClick = {
                    if (isConnected.value) coroutineScope.launch { bleScanner.disconnect() }
                    else bleScanner.scan()
                },
                enabled = !bleScanner.isScanning.value
            )

            Item(
                stringResource(Res.string.menu_exit),
                onClick = {
                    coroutineScope.launch {
                        exitApplication()
                    }
                }
            )
        }
    )
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}