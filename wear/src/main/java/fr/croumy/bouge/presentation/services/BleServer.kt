package fr.croumy.bouge.presentation.services

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.content.pm.PackageManager
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.util.UUID
import javax.inject.Inject

class BleServer @Inject constructor(
    private val context: Context,
    private val companionService: CompanionService,
    private val permissionService: PermissionService
) {
    companion object {
        const val SERVICE_UUID = "0000A3EF-0000-1000-8000-00805F9B34FB"
        const val READ_CHARACTERISTIC_UUID = "000087FA-0000-1000-8000-00805F9B34FB"
        const val WRITE_CHARACTERISTIC_UUID = "00004B62-0000-1000-8000-00805F9B34FB"
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    lateinit var currentCompanion: fr.croumy.bouge.core.models.companion.Companion
    val isAdvertising = MutableStateFlow(false)
    val isConnected = MutableStateFlow(false)
    val isSent = MutableStateFlow(false)

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val advertiser get() = bluetoothManager.adapter.bluetoothLeAdvertiser

    private val advertiseSettings = AdvertiseSettings.Builder()
        .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
        .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
        .setConnectable(true)
        .build()

    private val advertiseData = AdvertiseData.Builder()
        .addServiceUuid(ParcelUuid.fromString(SERVICE_UUID))
        .build()

    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            Log.i("BLESERVER","BLE Advertising started successfully: $settingsInEffect")
            super.onStartSuccess(settingsInEffect)
        }

        override fun onStartFailure(errorCode: Int) {
            Log.e("BLESERVER","BLE Advertising failed to start with error code: $errorCode")
            isAdvertising.value = false
            super.onStartFailure(errorCode)
        }
    }

    var gattServer: BluetoothGattServer? = null

    private val readCharacteristic = BluetoothGattCharacteristic(
        UUID.fromString(READ_CHARACTERISTIC_UUID),
        BluetoothGattCharacteristic.PROPERTY_READ,
        BluetoothGattCharacteristic.PERMISSION_READ
    )

    private val writeCharacteristic = BluetoothGattCharacteristic(
        UUID.fromString(WRITE_CHARACTERISTIC_UUID),
        BluetoothGattCharacteristic.PROPERTY_WRITE,
        BluetoothGattCharacteristic.PERMISSION_WRITE
    )

    private val bleService = BluetoothGattService(
        UUID.fromString(SERVICE_UUID),
        BluetoothGattService.SERVICE_TYPE_PRIMARY,
    ).apply {
        addCharacteristic(readCharacteristic)
        addCharacteristic(writeCharacteristic)
    }

    init {
        coroutineScope.launch {
            currentCompanion = companionService.myCompanion.first()!!
        }
    }

    private val serverCallback = object : BluetoothGattServerCallback() {
        override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d("BleServer", "Device connected: ${device?.address}")
                stopAdvertising()
                isConnected.value = true
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d("BleServer", "Device disconnected: ${device?.address}")
                coroutineScope.launch {
                    companionService.retrieveFromDesktop()
                    isSent.value = false
                    isConnected.value = false
                }
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onCharacteristicReadRequest(
            device: BluetoothDevice,
            requestId: Int,
            offset: Int,
            characteristic: BluetoothGattCharacteristic
        ) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic)

            Log.d("BleServer", "Read request for ${characteristic.uuid}")

            if (characteristic.uuid == UUID.fromString(READ_CHARACTERISTIC_UUID)) {
                val response = currentCompanion.encodeToJson().toByteArray(StandardCharsets.UTF_8)
                companionService.sendToDesktop()
                isSent.value = true
                gattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, response)
            } else {
                gattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, offset, null)
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onCharacteristicWriteRequest(
            device: BluetoothDevice,
            requestId: Int,
            characteristic: BluetoothGattCharacteristic,
            preparedWrite: Boolean,
            responseNeeded: Boolean,
            offset: Int,
            value: ByteArray?
        ) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value)

            if (characteristic.uuid == UUID.fromString(WRITE_CHARACTERISTIC_UUID)) {
                val receivedString = value?.toString(StandardCharsets.UTF_8) ?: ""
                Log.d("BleServer", "Write request received: $receivedString")

                //TODO: Handle received data (bonuses)
                coroutineScope.launch {
                    companionService.retrieveFromDesktop(emptyList())
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startAdvertising() {
        if (permissionService.isBluetoothPermissionsGranted()) {
            //OPEN SERVER
            if (gattServer == null) {
                gattServer = bluetoothManager.openGattServer(context, serverCallback)
                gattServer?.addService(bleService)
            }

            advertiser.startAdvertising(advertiseSettings, advertiseData, advertiseCallback)
            isAdvertising.value = true
        }
    }

    @SuppressLint("MissingPermission")
    fun stopAdvertising() {
        if (permissionService.isBluetoothPermissionsGranted()) {
            advertiser?.stopAdvertising(advertiseCallback)
            isAdvertising.value = false
        }
    }

    @SuppressLint("MissingPermission")
    fun closeServer() {
        gattServer?.clearServices()
        gattServer?.close()
        gattServer = null
    }
}