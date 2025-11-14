package fr.croumy.bouge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.ui.components.AnimatedSprite
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
        val companion = remember { mutableStateOf<Companion?>(null) }

        LaunchedEffect(Unit) {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val serverSocket = aSocket(selectorManager)
                .tcp()
                .bind("192.168.1.199", 9002)

            println("Server is listening at ${serverSocket.localAddress}")

            val socket = serverSocket.accept()
            println("Accepted $socket")
            val receiveChannel = socket.openReadChannel()
            try {
                while (true) {
                    val companionData = receiveChannel.readUTF8Line()
                    println(companionData)
                    if(companionData != null) {
                        companion.value = Companion.decodeFromJson(companionData)
                    }
                }
            } catch (e: Throwable) {
                socket.close()
            }
        }

        Column {
            if(companion.value != null) {
                Text(companion.value!!.name)
                Text(companion.value!!.age.toString())
                AnimatedSprite(
                    modifier = Modifier.size(120.dp),
                    imageId = companion.value!!.type.assetIdleId,
                    frameCount = companion.value!!.type.assetIdleFrame,
                )
            } else {
                Text("No companion data received yet.")
            }
        }
    }
}