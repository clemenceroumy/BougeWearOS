package fr.croumy.bouge.services

import androidx.compose.runtime.mutableStateOf
import com.juul.kable.Bluetooth
import com.juul.kable.Peripheral
import com.juul.kable.PlatformAdvertisement
import com.juul.kable.Scanner
import com.juul.kable.State
import com.juul.kable.WriteType
import com.juul.kable.characteristicOf
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine
import fr.croumy.bouge.core.mocks.companionMock
import fr.croumy.bouge.core.models.companion.Companion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class BleScanner(
    val companionService: CompanionService
) {
    val scanCoroutineScope = CoroutineScope(Dispatchers.IO)

    val isScanning = mutableStateOf(false)
    val isConnected = MutableStateFlow(false)
    val peripherals = mutableStateOf<List<PlatformAdvertisement>>(emptyList())
    val selectedPeripheral = MutableStateFlow<Peripheral?>(null)
    var peripheralState = selectedPeripheral
        .flatMapLatest { peripheral -> peripheral?.state ?: flowOf(State.Disconnected()) }
        .stateIn(
            scanCoroutineScope,
            SharingStarted.Lazily,
            State.Disconnected()
        )


    val readCharacteristic = characteristicOf(
        service = Bluetooth.BaseUuid + 0xA3EF,
        characteristic = Bluetooth.BaseUuid + 0x87FA
    )

    val writeCharacteristic = characteristicOf(
        service = Bluetooth.BaseUuid + 0xA3EF,
        characteristic = Bluetooth.BaseUuid + 0x4B62
    )

    init {
        CoroutineScope(Dispatchers.IO).launch {
            peripheralState.collect { state ->
                when {
                    state is State.Connected -> {
                        println("Connected to peripheral")
                        isConnected.value = true

                        readCompanion()
                    }

                    state is State.Connecting -> isScanning.value = false
                    state is State.Disconnected && isConnected.value -> {
                        println("Disconnected from peripheral")
                        isConnected.value = false
                        peripherals.value = emptyList()
                        selectedPeripheral.value = null
                        companionService.currentCompanion.value = null
                    }

                    else -> {}
                }
            }
        }
    }

    fun scan() {
        val scanner = Scanner {
            filters {
                match { services = listOf(Bluetooth.BaseUuid + 0xA3EF) }
            }
            logging {
                engine = SystemLogEngine
                level = Logging.Level.Warnings
                format = Logging.Format.Multiline
            }
        }

        scanCoroutineScope.launch {
            scanner.advertisements
                .onStart { isScanning.value = true }
                .onCompletion {
                    println("Scan completed ${it ?: ""}")
                    isScanning.value = false
                }
                .filter { advertisement ->
                    val someContains = peripherals.value.find { it.peripheralName == advertisement.peripheralName }
                    someContains == null
                }
                .takeWhile { selectedPeripheral.value == null }
                .collect {
                    println(it)
                    peripherals.value = peripherals.value.plus(it)
                }
        }
    }

    fun connectPeripheral(peripheral: PlatformAdvertisement) {
        selectedPeripheral.value = Peripheral(peripheral) {
            logging { level = Logging.Level.Events }
        }

        scanCoroutineScope.launch {
            try {
                selectedPeripheral.value!!.connect()
            } catch (e: Exception) {
                println("Error connecting to peripheral: $e")
            }
        }
    }

    suspend fun readCompanion() {
        /* TODO:
          * There's currently an issue with service detection on LINUX in Kable lib (cf.https://github.com/JuulLabs/kable/issues/989)
          * Due to this, reading charac fail on LINUX
          * On WINDOWS, everything works fine
        */
        try {
            val result = selectedPeripheral.value?.read(readCharacteristic)
            val resultString = result?.decodeToString()

            if (resultString != null) {
                companionService.currentCompanion.value = Companion.decodeFromJson(resultString)
            }
        } catch (e: Exception) {
            println("Error reading characteristic: $e")
        }
    }

    suspend fun writeCompanion() {
        try {
            selectedPeripheral.value?.write(
                writeCharacteristic,
                "".encodeToByteArray()
            )
            delay(500L)
            selectedPeripheral.value?.disconnect()
        } catch (e: Exception) {
            println("Error writing characteristic: $e")
        }
    }
}