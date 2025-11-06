package fr.croumy.bouge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val selectorManager = SelectorManager(Dispatchers.IO)


        setContent {
            val errorMessage = remember { mutableStateOf("") }

            suspend fun connectToServer() {
                try {
                    val socket = aSocket(selectorManager)
                        .tcp()
                        .connect("192.168.1.48", 9002)

                    val sendChannel = socket.openWriteChannel(autoFlush = true)
                    sendChannel.writeStringUtf8("helloworld\n")
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorMessage.value = "Error: ${e.message}"
                }
            }

            Scaffold {
                Column(Modifier.padding(it)) {
                    ElevatedButton(
                        onClick = { CoroutineScope(Dispatchers.IO).launch { connectToServer() } }
                    ) { Text("Connect") }
                    Text(errorMessage.value)
                }
            }
        }
    }
}