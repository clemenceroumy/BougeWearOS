package fr.croumy.bouge.ui.tray

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowPosition
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.menu_connect
import bouge.desktop.generated.resources.menu_disconnect
import bouge.desktop.generated.resources.menu_exit
import bouge.desktop.generated.resources.tray_icon_dark
import bouge.desktop.generated.resources.tray_icon_light
import com.kdroid.composetray.tray.api.ExperimentalTrayAppApi
import com.kdroid.composetray.tray.api.Tray
import com.kdroid.composetray.utils.getTrayWindowPosition
import com.kdroid.composetray.utils.isMenuBarInDarkMode
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.helpers.UnspecifiedWindowPosition
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.theme.BougeTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalTrayAppApi::class)
@Composable
fun ApplicationScope.TrayComponent(
    bleScanner: BleScanner = koinInject()
) {
    val coroutineScope = rememberCoroutineScope()
    val isMenuBarDark = isMenuBarInDarkMode()
    val windowPosition = mutableStateOf<WindowPosition>(UnspecifiedWindowPosition)
    val isOpen = mutableStateOf(false)

    val isConnected = bleScanner.isConnected.collectAsState()

    val exitLabel = stringResource(Res.string.menu_exit)
    val connectLabel = stringResource(Res.string.menu_connect)
    val disconnectLabel = stringResource(Res.string.menu_disconnect)

    LaunchedEffect(isConnected.value) {
        if (isConnected.value) {
            isOpen.value = false
        }
    }

    Tray(
        icon = painterResource(if(isMenuBarDark) Res.drawable.tray_icon_dark else Res.drawable.tray_icon_light),
        tooltip = "",
        menuContent = {
            Item(
                label = if (isConnected.value) disconnectLabel else connectLabel,
                onClick = {
                    if (isConnected.value) coroutineScope.launch { bleScanner.disconnect() }
                    else {
                        bleScanner.scan()
                        windowPosition.value = getTrayWindowPosition(Window.MENU_WIDTH, Window.MENU_HEIGHT)
                        isOpen.value = true
                    }
                },
            )
            Item(
                label = exitLabel,
                onClick = { exitApplication() }
            )
        }
    )

    BougeTheme {
        TrayMenuComponent(
            position = windowPosition.value,
            isOpen = isOpen,
        )
    }
}