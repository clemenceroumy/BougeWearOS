package fr.croumy.bouge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
        val isConnected = BleScanner.isConnected.collectAsState()
        val companion = remember { mutableStateOf<Companion?>(null) }

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
                if(!BleScanner.isScanning.value) {
                    Button(
                        onClick = { BleScanner.scan() }
                    ) {
                        Text("START SERVER")
                    }
                } else if(!isConnected.value) {
                    Text("Scanning for connections...")
                    BleScanner.peripherals.value.map {
                        Row {
                            Text(
                                it.toString(),
                                modifier = Modifier.weight(1f)
                            )
                            Button(
                                onClick = { BleScanner.selectPeripheral(it) }
                            ) {
                                Text("CONNECT")
                            }
                        }
                    }
                } else {
                    Text("Connected to peripheral, waiting for data...")
                }
            }
        }
    }
}