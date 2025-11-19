package fr.croumy.bouge.presentation.services

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresPermission
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class BleServer @Inject constructor(
    private val context: Context
) {
    companion object {
        const val SERVICE_UUID = "0000A3EF-0000-1000-8000-00805F9B34FB"
        const val READ_CHARACTERISTIC_UUID = "000087FA-0001-1000-8000-00805F9B34FB"
        const val WRITE_CHARACTERISTIC_UUID = "00004B62-0002-1000-8000-00805F9B34FB"
    }

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val advertiser get() = bluetoothManager.adapter.bluetoothLeAdvertiser

    private val advertiseSettings = AdvertiseSettings.Builder()
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

    private val serverCallback = object : BluetoothGattServerCallback() {
        override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
            /* newState values:
                int STATE_DISCONNECTED = 0;
                int STATE_CONNECTING = 1;
                int STATE_CONNECTED = 2;
                int STATE_DISCONNECTING = 3;
             */
            Log.i("BLESERVER","Connection state changed: $device, status: $status, newState: $newState")
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onCharacteristicReadRequest(
            device: BluetoothDevice?,
            requestId: Int,
            offset: Int,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic)
            Log.i("BLESERVER","Characteristic read request: $device, requestId: $requestId, offset: $offset, characteristic: $characteristic")
            gattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic?.value)
        }

        override fun onCharacteristicWriteRequest(
            device: BluetoothDevice?,
            requestId: Int,
            characteristic: BluetoothGattCharacteristic?,
            preparedWrite: Boolean,
            responseNeeded: Boolean,
            offset: Int,
            value: ByteArray?
        ) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value)
            Log.i("BLESERVER","Characteristic write request: $device, requestId: $requestId, characteristic: $characteristic, preparedWrite: $preparedWrite, responseNeeded: $responseNeeded, offset: $offset, value: ${value?.decodeToString()}")
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun start() {
        //OPEN SERVER
        if (gattServer == null) {
            gattServer = bluetoothManager.openGattServer(context, serverCallback)
            gattServer?.addService(bleService)
        }

        advertiser.startAdvertising(advertiseSettings, advertiseData, advertiseCallback)
    }

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_ADVERTISE])
    fun stop() {
        advertiser?.stopAdvertising(advertiseCallback)

        // CLOSE SERVER
        gattServer?.clearServices()
        gattServer?.close()
        gattServer = null
    }
}