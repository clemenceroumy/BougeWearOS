package fr.croumy.bouge.presentation.services

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import javax.inject.Inject


class BleScanner @Inject constructor(
    private val context: Context
) {

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private var isScanning = false
    private val handler = Handler(Looper.getMainLooper())

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val device = result.device
            val deviceName = device.name
            val deviceAddress = device.address

            if (deviceName != null) {
                Log.d("BleScanner", "Found device: $deviceName ($deviceAddress)")
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.e("BleScanner", "Scan failed with error code: $errorCode")
        }
    }

    fun startScan(scanPeriod: Long = 10000) {
        if (isScanning || !bluetoothAdapter.isEnabled) return

        val filters: List<ScanFilter> = emptyList()

        isScanning = true
        bleScanner.startScan(filters, scanSettings, scanCallback)
        Log.d("BleScanner", "Scan started.")

        handler.postDelayed({
            stopScan()
        }, scanPeriod)
    }

    fun stopScan() {
        if (isScanning) {
            isScanning = false
            bleScanner.stopScan(scanCallback)
            Log.d("BleScanner", "Scan stopped.")
        }
    }
}