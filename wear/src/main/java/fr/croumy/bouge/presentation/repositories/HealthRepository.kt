package fr.croumy.bouge.presentation.repositories

import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.services.DataService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.TimeZone
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

            if (dataPoints.dataTypes.contains(DataType.STEPS_DAILY)) {
                val dataPointStepDaily = dataPoints.intervalDataPoints.firstOrNull { it.dataType == DataType.STEPS_DAILY }

                val totalStepsToday = dataPointStepDaily?.value as Long
                dataService._totalSteps.value = totalStepsToday.toInt()
            }

            if(dataPoints.dataTypes.contains(DataType.STEPS)) {
                val dataPointSteps = dataPoints.intervalDataPoints.first { it.dataType == DataType.STEPS }
                val steps = dataPointSteps.value as Long

                // EVERY EVENT RECEIVED HERE MEANS A STEP HAS BEEN DETECTED
                dataService._isWalking.value = true
                dataService.lastStepTime.value = ZonedDateTime.now()

                // ADD STEPS TO THE CURRENT WALK
                dataService._currentWalk.value += steps.toInt()
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