package fr.croumy.bouge.presentation.services

import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.MainActivity
import timber.log.Timber
import java.time.ZonedDateTime
import javax.inject.Inject

@AndroidEntryPoint
class HealthService @Inject constructor(): PassiveListenerService() {
    @Inject lateinit var dataService: DataService
    val healthClient = HealthServices.getClient(MainActivity.Companion.context)
    val passiveMonitoringClient = healthClient.passiveMonitoringClient

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = setOf(DataType.Companion.STEPS_DAILY, DataType.Companion.STEPS),
        shouldUserActivityInfoBeRequested = false,
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    val passiveListenerCallback = object : PassiveListenerCallback {
        override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
            super.onNewDataPointsReceived(dataPoints)

            if (dataPoints.dataTypes.contains(DataType.STEPS_DAILY)) {
                val dataPointStepDaily = dataPoints.getData(DataType.STEPS_DAILY).last()

                val totalStepsToday = dataPointStepDaily.value
                dataService._totalSteps.value = totalStepsToday.toInt()
            }

            if(dataPoints.dataTypes.contains(DataType.STEPS)) {
                val dataPointSteps = dataPoints.getData(DataType.STEPS)
                val totalStepsSinceLastCheck = dataPointSteps.fold(initial = 0) {
                        acc, dataPoint -> acc + (dataPoint.value.toInt())
                }

                // EVERY EVENT RECEIVED HERE MEANS A STEP HAS BEEN DETECTED
                dataService._isWalking.value = true
                dataService.lastStepTime.value = ZonedDateTime.now()

                // ADD STEPS TO THE CURRENT WALK
                dataService._currentWalk.value += totalStepsSinceLastCheck
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("HealthService created")
    }

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        super.onNewDataPointsReceived(dataPoints)

        if (dataPoints.dataTypes.contains(DataType.Companion.STEPS_DAILY)) {
            val dataPointStepDaily = dataPoints.getData(DataType.STEPS_DAILY).last()

            val totalStepsToday = dataPointStepDaily.value
            dataService._totalSteps.value = totalStepsToday.toInt()
        }

        if(dataPoints.dataTypes.contains(DataType.STEPS)) {
            val dataPointSteps = dataPoints.getData(DataType.STEPS)
            val totalStepsSinceLastCheck = dataPointSteps.fold(initial = 0) {
                acc, dataPoint -> acc + (dataPoint.value.toInt())
            }

            // EVERY EVENT RECEIVED HERE MEANS A STEP HAS BEEN DETECTED
            dataService._isWalking.value = true
            dataService.lastStepTime.value = ZonedDateTime.now()

            // ADD STEPS TO THE CURRENT WALK
            dataService._currentWalk.value += totalStepsSinceLastCheck
        }
    }

    // USED WHEN APP IS RUNNING IN BACKGROUND (less reactive)
    fun initHealthService() {
        unregisterHealthCallback()

        passiveMonitoringClient.setPassiveListenerServiceAsync(
            HealthService::class.java,
            passiveListenerConfig
        )
    }

    // USED WHEN APP IS RUNNING IN FOREGROUND
    fun initHealthCallback() {
        unregisterHealthService()

        passiveMonitoringClient.setPassiveListenerCallback(
            passiveListenerConfig,
            passiveListenerCallback
        )
    }

    fun unregisterHealthService() {
        passiveMonitoringClient.clearPassiveListenerServiceAsync()
    }

    fun unregisterHealthCallback() {
        passiveMonitoringClient.clearPassiveListenerCallbackAsync()
    }
}