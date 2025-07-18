package fr.croumy.bouge.presentation.repositories

import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.services.DataService
import javax.inject.Inject

class HealthRepository @Inject constructor(
    val dataService: DataService
) {
    val healthClient = HealthServices.getClient(MainActivity.context)
    val passiveMonitoringClient = healthClient.passiveMonitoringClient

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = setOf(DataType.STEPS_DAILY),
        shouldUserActivityInfoBeRequested = false,
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    // Using callback instead of Service because we don't need to receive updates on background mode
    // cf. https://developer.android.com/health-and-fitness/guides/health-services/monitor-background#register
    val passiveListenerCallback = object : PassiveListenerCallback {
        override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
            super.onNewDataPointsReceived(dataPoints)

            if(dataPoints.dataTypes.contains(DataType.STEPS_DAILY)) {
                val totalStepsToday = dataPoints.intervalDataPoints.first().value as Long
                dataService._totalSteps.value = totalStepsToday.toInt()
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