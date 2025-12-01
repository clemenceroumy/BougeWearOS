package fr.croumy.bouge.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.menu_connect
import bouge.desktop.generated.resources.menu_disconnect
import bouge.desktop.generated.resources.menu_exit
import fr.croumy.bouge.TrayIcon
import fr.croumy.bouge.services.BleScanner
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import fr.croumy.bouge.constants.Window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
                    if (isConnected.value) coroutineScope.launch { bleScanner.writeCompanion() }
                    else bleScanner.scan()
                },
                enabled = !bleScanner.isScanning.value
            )

            Item(
                stringResource(Res.string.menu_exit),
                onClick = {
                    coroutineScope.launch {
                        if(isConnected.value) bleScanner.writeCompanion()
                        exitApplication()
                    }
                }
            )
        }
    )
}