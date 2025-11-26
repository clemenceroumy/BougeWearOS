package fr.croumy.bouge.presentation.ui.screens.connect

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.BleServer
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
    val companionService: CompanionService,
    val bleServer: BleServer
): ViewModel() {
    val isAdvertising = bleServer.isAdvertising
    val isConnected = bleServer.isConnected
    val isSent = bleServer.isSent

    val companion = companionService.myCompanion.stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun connectToServer() {
        bleServer.startAdvertising()
    }
}