package fr.croumy.bouge.ui.tray

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import bouge.core.generated.resources.background_sky_day
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.services.CompanionService
import fr.croumy.bouge.theme.Dimensions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import bouge.core.generated.resources.Res as CoreRes

@Composable
fun TrayMenuComponent(
    companionService: CompanionService = koinInject(),
    bleScanner: BleScanner = koinInject(),
    position: MutableState<WindowPosition>,
    isOpen: MutableState<Boolean>,
) {
    val state = rememberDialogState(size = DpSize(Window.MENU_WIDTH.dp, Window.MENU_HEIGHT.dp))

    val companion = companionService.currentCompanion
    val peripherals = bleScanner.peripherals
    val selectedPeripheral = bleScanner.selectedPeripheral.collectAsState()
    val isConnected = bleScanner.isConnected.collectAsState()
    val isSearching = bleScanner.isScanning.value || (companion.value == null && selectedPeripheral.value != null)

    LaunchedEffect(position.value) {
        state.position = position.value
    }

    LaunchedEffect(isConnected.value, isOpen.value) {
        // auto scan on open menu
        if(!isConnected.value && isOpen.value) {
            bleScanner.scan()
        }
    }

    DialogWindow(
        onCloseRequest = { },
        state = state,
        undecorated = true,
        transparent = true,
        visible = isOpen.value,
        alwaysOnTop = true,
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painterResource(CoreRes.drawable.background_sky_day),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(Dimensions.mediumRadius))
            )
            Column(
                Modifier.fillMaxSize()
            ) {
                if (isSearching) {
                    if (peripherals.value.isEmpty()) {
                        CircularProgressIndicator(Modifier.size(Dimensions.mediumIcon))
                    } else peripherals.value.forEach {
                        TextButton(
                            onClick = { bleScanner.connectPeripheral(it) }
                        ) {
                            Row {
                                Text(
                                    it.identifier.toString(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.size(Dimensions.smallPadding))
                                if (selectedPeripheral.value?.identifier == it.identifier) {
                                    CircularProgressIndicator(
                                        Modifier.size(Dimensions.smallIcon),
                                        strokeWidth = 2.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}