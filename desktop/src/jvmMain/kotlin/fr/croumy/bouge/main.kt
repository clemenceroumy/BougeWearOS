package fr.croumy.bouge

import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.awaitApplication
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.read
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.skia.skottie.Logger

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BougeWearOS",
    ) {
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
                        val name = receiveChannel.readUTF8Line()
                        println(name)
                    }
                } catch (e: Throwable) {
                    socket.close()
                }
            }
        }

        Text("WINDOWS")
    }
}