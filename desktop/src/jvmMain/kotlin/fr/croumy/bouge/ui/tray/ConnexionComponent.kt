package fr.croumy.bouge.ui.tray

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import androidx.compose.ui.window.rememberWindowState
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.services.BleScanner
import org.koin.compose.koinInject
import javax.swing.Box

@Composable
fun BoxScope.ConnexionComponent(
    bleScanner: BleScanner = koinInject(),
    isDialogOpen: Boolean,
) {
    val state = rememberDialogState(
        position = WindowPosition.Aligned(Alignment.BottomEnd),
        size = DpSize(Window.WIDTH.dp, Window.HEIGHT.dp)
    )

    val peripherals = bleScanner.peripherals
    val selectedPeripheral = bleScanner.selectedPeripheral.collectAsState()

    DialogWindow(
        onCloseRequest = {  },
        state = state,
        undecorated = true,
        transparent = true,
        visible = isDialogOpen
    ) {
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(10.dp))
        ) {
            if (peripherals.value.isEmpty()) {
                CircularProgressIndicator(Modifier.size(30.dp))
            } else peripherals.value.map {
                TextButton(
                    onClick = { bleScanner.connectPeripheral(it) }
                ) {
                    Row {
                        Text(
                            it.identifier.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.size(5.dp))
                        if(selectedPeripheral.value?.identifier == it.identifier) {
                            CircularProgressIndicator(
                                Modifier.size(15.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }
}