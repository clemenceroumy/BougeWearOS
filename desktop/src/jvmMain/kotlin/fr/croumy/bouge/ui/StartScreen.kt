package fr.croumy.bouge.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.services.CompanionService
import fr.croumy.bouge.ui.main.MainScreen
import fr.croumy.bouge.ui.tray.ConnexionComponent
import org.koin.compose.koinInject

@Composable
fun StartScreen(
    companionService: CompanionService = koinInject(),
    bleScanner: BleScanner = koinInject(),
) {
    val companion = companionService.currentCompanion
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