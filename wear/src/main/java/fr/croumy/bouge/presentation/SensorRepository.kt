package fr.croumy.bouge.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import fr.croumy.bouge.presentation.services.SensorListener
import jakarta.inject.Inject

class SensorRepository @Inject constructor(
    private val sensorListener: SensorListener
) {
    private val sensorManager = MainActivity.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    fun initSensors() {
        val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        val heartRate: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        sensorManager.registerListener(sensorListener, heartRate, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregisterSensors() {
        sensorManager.unregisterListener(sensorListener)
    }
}