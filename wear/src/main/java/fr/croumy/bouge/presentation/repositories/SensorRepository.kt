package fr.croumy.bouge.presentation.repositories

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.services.SensorListener
import jakarta.inject.Inject

class SensorRepository @Inject constructor(
    private val sensorListener: SensorListener,
    private val context: Context
) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    fun initSensors() {
        val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        val heartRate: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        sensorManager.registerListener(sensorListener, heartRate, SensorManager.SENSOR_DELAY_NORMAL)

        val stepDetector: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(sensorListener, stepDetector, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregisterSensors() {
        sensorManager.unregisterListener(sensorListener)
    }
}