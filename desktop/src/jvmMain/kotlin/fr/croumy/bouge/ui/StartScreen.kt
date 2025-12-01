package fr.croumy.bouge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.ui.main.MainScreen
import fr.croumy.bouge.ui.tray.ConnexionComponent
import org.koin.compose.koinInject

@Composable
fun StartScreen(
    bleScanner: BleScanner = koinInject(),
) {
    val companion = bleScanner.currentCompanion
    val selectedPeripheral = bleScanner.selectedPeripheral.collectAsState()
    val isOpen = bleScanner.isScanning.value || (companion.value == null && selectedPeripheral.value != null)

    Box(Modifier.fillMaxSize()) {
        if (companion.value != null) {
            MainScreen(companion.value!!)
        }

        ConnexionComponent(
            isDialogOpen = isOpen,
        )
    }
}