package fr.croumy.bouge.presentation.services

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import fr.croumy.bouge.presentation.extensions.round
import fr.croumy.bouge.presentation.models.AccelerometerValue
import javax.inject.Inject

class SensorListener @Inject constructor(
    val sensorService: SensorService
) : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_HEART_RATE -> {
                    val heartRate = it.values[0]

                    sensorService._heartrateValue.value = heartRate.toInt()
                }
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = it.values[0].round(2)
                    val y = it.values[1].round(2)
                    val z = it.values[2].round(2)

                    sensorService._accelerometerValue.value = AccelerometerValue(x, y, z)
                }
                Sensor.TYPE_STEP_DETECTOR -> {
                    // EVERY EVENT RECEIVED HERE MEANS A STEP HAS BEEN DETECTED
                    sensorService._isWalking.value = true
                    sensorService.lastStepTime.value = System.currentTimeMillis()
                }
                else -> {}
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}