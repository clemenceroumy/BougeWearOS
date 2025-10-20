package fr.croumy.bouge.presentation.services

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import fr.croumy.bouge.presentation.extensions.round
import fr.croumy.bouge.presentation.models.AccelerometerValue
import javax.inject.Inject

class SensorListener @Inject constructor(
    val dataService: DataService
) : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = it.values[0].round(2)
                    val y = it.values[1].round(2)
                    val z = it.values[2].round(2)

                    dataService.retrieveGyroSensor(AccelerometerValue(x, y, z))
                }
                Sensor.TYPE_GYROSCOPE -> {
                    Log.i("sensor listener","Gyroscope data: x=${it.values[0]}, y=${it.values[1]}, z=${it.values[2]}")
                }
                else -> {}
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}