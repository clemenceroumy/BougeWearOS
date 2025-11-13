package fr.croumy.bouge

import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.Dispatchers

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BougeWearOS",
    ) {
        val companion = mutableStateOf("")

        LaunchedEffect(Unit) {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val serverSocket = aSocket(selectorManager)
                .tcp()
                .bind("192.168.1.199", 9002)

            println("Server is listening at ${serverSocket.localAddress}")

            while (true) {
                val socket = serverSocket.accept()
                println("Accepted $socket")
                val receiveChannel = socket.openReadChannel()
                try {
                    while (true) {
                        val companionData = receiveChannel.readUTF8Line()
                        println(companionData)
                        if(companionData != null) {
                            companion.value = companionData
                        }
                    }
                } catch (e: Throwable) {
                    socket.close()
                }
            }
        }

        Text("WINDOWS")
        Text(companion.value)
    }
}