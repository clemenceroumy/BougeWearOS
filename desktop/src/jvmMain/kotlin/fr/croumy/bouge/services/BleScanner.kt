package fr.croumy.bouge.services

import androidx.compose.runtime.mutableStateOf
import com.juul.kable.Bluetooth
import com.juul.kable.Peripheral
import com.juul.kable.PlatformAdvertisement
import com.juul.kable.Scanner
import com.juul.kable.State
import com.juul.kable.characteristicOf
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine
import fr.croumy.bouge.core.models.companion.Companion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object BleScanner {
    val isScanning = mutableStateOf(false)
    val currentCompanion = mutableStateOf<Companion?>(null)
    val peripherals = mutableStateOf<List<PlatformAdvertisement>>(emptyList())

    val selectedPeripheral = MutableStateFlow<Peripheral?>(null)
    var peripheralState: StateFlow<State> = MutableStateFlow<State>(State.Disconnected())
    lateinit var connectionScope: CoroutineScope

    @OptIn(ExperimentalCoroutinesApi::class)
    val isConnected: StateFlow<Boolean> = selectedPeripheral
        .flatMapLatest { peripheral -> peripheral?.state?.map { state -> state is State.Connected } ?: flowOf(false) }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = false
        )

    val readCharacteristic = characteristicOf(
        service = Bluetooth.BaseUuid + 0xA3EF,
        characteristic = Bluetooth.BaseUuid + 0x87FA
    )

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
    val scannerFlow = scanner.advertisements

    init {
        CoroutineScope(Dispatchers.IO).launch {
            selectedPeripheral
                .filterNotNull()
                .take(1)
                .collect {
                    peripheralState = it.state
                }

            peripheralState.collect { state ->
                if(state is State.Connected) {
                    readCompanion()
                } else if(state is State.Disconnected) {
                    peripherals.value = emptyList()
                }
            }
        }
    }

    fun scan() {
        CoroutineScope(Dispatchers.IO).launch {
            scannerFlow
                .onStart { isScanning.value = true }
                .takeWhile { selectedPeripheral.value == null }
                .filter { advertisement ->
                    val someContains = peripherals.value.find { it.peripheralName == advertisement.peripheralName }
                    someContains == null
                }
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

        CoroutineScope(Dispatchers.IO).launch {
            try {
                connectionScope = selectedPeripheral.value!!.connect()
            } catch (e: Exception) {
                println("Error connecting to peripheral: $e" )
            }
        }
    }

    suspend fun readCompanion() {
        /*
          * There's currently an issue with service detection on LINUX in Kable lib (cf.https://github.com/JuulLabs/kable/issues/989)
          * Due to this, reading charac fail on LINUX
          * On WINDOWS, everything works fine
        */
        try {
            val result = selectedPeripheral.value?.read(readCharacteristic)
            val resultString = result?.decodeToString()

            if(resultString != null) {
                currentCompanion.value = Companion.decodeFromJson(resultString)
            }
        } catch (e: Exception) {
            println("Error reading characteristic: $e")
        }
    }
}