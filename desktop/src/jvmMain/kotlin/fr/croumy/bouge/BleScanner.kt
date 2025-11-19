package fr.croumy.bouge

import androidx.compose.runtime.mutableStateOf
import com.juul.kable.Bluetooth
import com.juul.kable.Filter
import com.juul.kable.Peripheral
import com.juul.kable.PlatformAdvertisement
import com.juul.kable.Scanner
import com.juul.kable.characteristicOf
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object BleScanner {
    val isScanning = mutableStateOf(false)
    val peripherals = mutableStateOf<List<PlatformAdvertisement>>(emptyList())

    val selectedPeripheral = mutableStateOf<Peripheral?>(null)

    val readCharacteristic = characteristicOf(
        service = Bluetooth.BaseUuid + 0xA3EF,
        characteristic = Bluetooth.BaseUuid + 0x87FA
    )

    val scanner = Scanner {
        filters {
            match {
                services = listOf(Bluetooth.BaseUuid + 0xA3EF)
            }
        }
        logging {
            engine = SystemLogEngine
            level = Logging.Level.Warnings
            format = Logging.Format.Multiline
        }
    }
    val scannerFlow = scanner.advertisements

    fun scan() {
        CoroutineScope(Dispatchers.IO).launch {
            scannerFlow
                .onStart { isScanning.value = true }
                .takeWhile { selectedPeripheral.value == null }
                .collect {
                    if (!peripherals.value.contains(it)) {
                        println("Found device: $it")
                        peripherals.value += it
                    }
                }
        }
    }

    fun selectPeripheral(peripheral: PlatformAdvertisement) {
        println("Selected peripheral: $peripheral")
        selectedPeripheral.value = Peripheral(peripheral) {
            logging {
                level = Logging.Level.Events // or Data
            }

            onServicesDiscovered {
                println(selectedPeripheral.value?.services?.value)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            selectedPeripheral.value?.state?.collect { state ->
                println("Peripheral state: $state" )
            }
        }

        runBlocking {
            try {
                selectedPeripheral.value!!.connect()
            } catch (e: Exception) {
                println("Error connecting to peripheral: $e" )
            }
        }
    }
}