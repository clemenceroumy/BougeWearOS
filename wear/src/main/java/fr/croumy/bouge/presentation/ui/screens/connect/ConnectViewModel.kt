package fr.croumy.bouge.presentation.ui.screens.connect

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(

): ViewModel() {
    val connectionError = mutableStateOf<String?>(null)

    suspend fun connectToServer() {
        val selectorManager = SelectorManager(Dispatchers.IO)

        try {
            val socket = aSocket(selectorManager)
                .tcp()
                .connect("192.168.1.199", 9002)

            val sendChannel = socket.openWriteChannel(autoFlush = true)
            sendChannel.writeStringUtf8("helloworld\n")
        } catch (e: Exception) {
            e.printStackTrace()
            connectionError.value = "Error: ${e.message}"
        }
    }
}