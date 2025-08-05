package fr.croumy.bouge.presentation.repositories

import android.util.Log
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import androidx.health.services.client.data.UserActivityInfo
import androidx.health.services.client.data.UserActivityState
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.services.DataService
import timber.log.Timber
import javax.inject.Inject

class HealthRepository @Inject constructor(
    val dataService: DataService
) {
    val healthClient = HealthServices.getClient(MainActivity.context)
    val passiveMonitoringClient = healthClient.passiveMonitoringClient

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = setOf(DataType.STEPS_DAILY, DataType.STEPS),
        shouldUserActivityInfoBeRequested = false,
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    // Using callback instead of Service because we don't need to receive updates on background mode
    // cf. https://developer.android.com/health-and-fitness/guides/health-services/monitor-background#register
    val passiveListenerCallback = object : PassiveListenerCallback {
        override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
            super.onNewDataPointsReceived(dataPoints)
            Timber.i("New data points received: $dataPoints")

            if (dataPoints.dataTypes.contains(DataType.STEPS_DAILY)) {
                val dataPointStepDaily = dataPoints.intervalDataPoints.firstOrNull { it.dataType == DataType.STEPS_DAILY }

                val totalStepsToday = dataPointStepDaily?.value as Long
                dataService._totalSteps.value = totalStepsToday.toInt()
            }

            if(dataPoints.dataTypes.contains(DataType.STEPS)) {
                val dataPointSteps = dataPoints.intervalDataPoints.firstOrNull { it.dataType == DataType.STEPS }

                // EVERY EVENT RECEIVED HERE MEANS A STEP HAS BEEN DETECTED
                dataService._isWalking.value = true
                dataService.lastStepTime.value = System.currentTimeMillis()

                // ADD A STEP TO THE CURRENT WALK
                val currentWalkNumberSteps = dataService._walks.value.lastOrNull()?.plus(1) ?: 1
                dataService._walks.value = dataService._walks.value.dropLast(1).plus(currentWalkNumberSteps)
            }
        }
    }

    fun initMeasure() {
        passiveMonitoringClient.setPassiveListenerCallback(
            passiveListenerConfig,
            passiveListenerCallback
        )
    }

    fun unregisterMeasure() {

    }
}