package fr.croumy.bouge.presentation.ui.screens.connect

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.CompanionService
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
    val companionService: CompanionService
): ViewModel() {
    val connectionError = mutableStateOf<String?>(null)
    val companion = companionService.myCompanion.stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    suspend fun connectToServer() {
        val selectorManager = SelectorManager(Dispatchers.IO)

        try {
            val socket = aSocket(selectorManager)
                .tcp()
                .connect("192.168.1.199", 9002) // TODO: REMOVE IP

            val sendChannel = socket.openWriteChannel(autoFlush = true)
            sendChannel.writeStringUtf8("${companion.value?.toString()}\n")
        } catch (e: Exception) {
            e.printStackTrace()
            connectionError.value = "Error: ${e.message}"
        }
    }
}