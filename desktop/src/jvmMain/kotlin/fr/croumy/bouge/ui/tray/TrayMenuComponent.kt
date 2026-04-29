package fr.croumy.bouge.ui.tray

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import bouge.core.generated.resources.background_sky_day
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.close
import bouge.desktop.generated.resources.description_close
import bouge.desktop.generated.resources.menu_search
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.core.theme.Dimensions
import fr.croumy.bouge.core.ui.components.WoodPanelComponent
import fr.croumy.bouge.helpers.GrassGenerator
import fr.croumy.bouge.helpers.OperatingSystem
import fr.croumy.bouge.helpers.UnspecifiedWindowPosition
import fr.croumy.bouge.helpers.currentOS
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.services.CompanionService
import fr.croumy.bouge.ui.components.Button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import bouge.core.generated.resources.Res as CoreRes

@Composable
fun TrayMenuComponent(
    companionService: CompanionService = koinInject(),
    bleScanner: BleScanner = koinInject(),
    position: WindowPosition,
    isOpen: MutableState<Boolean>,
) {
    val state = rememberWindowState(
        position = position,
        size = DpSize(Window.MENU_WIDTH.dp, Window.MENU_HEIGHT.dp)
    )

    val companion = companionService.currentCompanion
    val peripherals = bleScanner.peripherals
    val selectedPeripheral = bleScanner.selectedPeripheral.collectAsState()
    val isSearching = bleScanner.isScanning.value || (companion.value == null && selectedPeripheral.value != null)

    LaunchedEffect(position) {
        state.position = position
    }

    fun onClose() {
        isOpen.value = false
        bleScanner.stopScan()
    }

    Window(
        onCloseRequest = { onClose() },
        state = state,
        undecorated = true,
        transparent = currentOS != OperatingSystem.Linux,
        visible = isOpen.value && state.position != UnspecifiedWindowPosition,
        alwaysOnTop = true,
        resizable = false,
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painterResource(CoreRes.drawable.background_sky_day),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(Dimensions.mediumRadius))
            )

            if (isSearching) {
                if (peripherals.value.isEmpty()) {
                    WoodPanelComponent(
                        text = stringResource(Res.string.menu_search),
                        paddingTop = Dimensions.mediumPadding,
                        size = Dimensions.xlargeIcon
                    )
                } else Column(
                    Modifier
                        .fillMaxSize()
                        .scrollable(
                            rememberScrollState(),
                            orientation = Orientation.Vertical,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    peripherals.value.forEach {
                        Row {
                            Button(
                                text = it.name ?: it.identifier.toString(),
                                onClick = { bleScanner.connectPeripheral(it) },
                                icon = {
                                    if (selectedPeripheral.value?.identifier == it.identifier) {
                                        CircularProgressIndicator(
                                            Modifier.size(Dimensions.xxsmallIcon),
                                            strokeWidth = 2.dp
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(top = Dimensions.smallPadding, end = Dimensions.smallPadding),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    Modifier
                        .clip(CircleShape)
                        .clickable { onClose() }
                        .size(Dimensions.smallIcon)
                        .background(Color.Black.copy(alpha = 0.2f), shape = CircleShape)
                        .padding(Dimensions.xsmallPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(Res.drawable.close),
                        contentDescription = stringResource(Res.string.description_close),
                        modifier = Modifier.size(Dimensions.smallIcon)
                    )
                }
            }

            GrassGenerator(
                Modifier.clip(RoundedCornerShape(bottomStart = Dimensions.mediumRadius, bottomEnd = Dimensions.mediumRadius))
            )
        }
    }
}