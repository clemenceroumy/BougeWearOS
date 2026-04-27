package fr.croumy.bouge.ui.tray

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.ApplicationScope
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.menu_disconnect
import bouge.desktop.generated.resources.menu_exit
import com.kdroid.composetray.tray.api.ExperimentalTrayAppApi
import com.kdroid.composetray.tray.api.Tray
import com.kdroid.composetray.utils.getTrayWindowPosition
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.theme.BougeTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalTrayAppApi::class)
@Composable
fun ApplicationScope.TrayComponent(
    bleScanner: BleScanner = koinInject()
) {
    val coroutineScope = rememberCoroutineScope()
    val windowPosition = mutableStateOf(getTrayWindowPosition(Window.WIDTH, Window.HEIGHT))
    val isOpen = mutableStateOf(false)

    val isConnected = bleScanner.isConnected.collectAsState()

    val exitLabel = stringResource(Res.string.menu_exit)
    val disconnectLabel = stringResource(Res.string.menu_disconnect)

    LaunchedEffect(isConnected.value) {
        if(isConnected.value) { isOpen.value = false }
    }

    Tray(
        icon = TrayIcon,
        tooltip = "",
        primaryAction = {
            windowPosition.value = getTrayWindowPosition(Window.WIDTH, Window.HEIGHT)
            isOpen.value = true
        },
        menuContent = {
            Item(
                label = disconnectLabel,
                onClick = { coroutineScope.launch { bleScanner.disconnect() } },
                isEnabled = isConnected.value
            )
            Item(
                label = exitLabel,
                onClick = { exitApplication() }
            )
        }
    )

    BougeTheme {
        TrayMenuComponent(
            position = windowPosition,
            isOpen = isOpen,
        )
    }
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}