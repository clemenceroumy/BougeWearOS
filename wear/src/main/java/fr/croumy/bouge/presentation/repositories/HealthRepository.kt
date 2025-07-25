package fr.croumy.bouge.presentation.repositories

import android.util.Log
import androidx.health.services.client.ExerciseUpdateCallback
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.ExerciseConfig
import androidx.health.services.client.data.ExerciseEvent
import androidx.health.services.client.data.ExerciseInfo
import androidx.health.services.client.data.ExerciseLapSummary
import androidx.health.services.client.data.ExerciseType
import androidx.health.services.client.data.ExerciseUpdate
import androidx.health.services.client.data.PassiveListenerConfig
import androidx.health.services.client.getCurrentExerciseInfo
import androidx.health.services.client.startExercise
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.services.DataService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class HealthRepository @Inject constructor(
    val dataService: DataService
) {
    val healthClient = HealthServices.getClient(MainActivity.context)
    val passiveMonitoringClient = healthClient.passiveMonitoringClient
    val exerciseClient = healthClient.exerciseClient

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = setOf(DataType.STEPS_DAILY),
        shouldUserActivityInfoBeRequested = false,
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    val exerciseConfig = ExerciseConfig(
        exerciseType = ExerciseType.WALKING,
        dataTypes = setOf(),
        isAutoPauseAndResumeEnabled = true,
        isGpsEnabled = true,
    )

    // Using callback instead of Service because we don't need to receive updates on background mode
    // cf. https://developer.android.com/health-and-fitness/guides/health-services/monitor-background#register
    val passiveListenerCallback = object : PassiveListenerCallback {
        override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
            super.onNewDataPointsReceived(dataPoints)

            if (dataPoints.dataTypes.contains(DataType.STEPS_DAILY)) {
                val totalStepsToday = dataPoints.intervalDataPoints.first().value as Long
                dataService._totalSteps.value = totalStepsToday.toInt()
            }
        }
    }

    val exerciseCallback = object : ExerciseUpdateCallback {
        override fun onAvailabilityChanged(
            dataType: DataType<*, *>,
            availability: Availability
        ) {
        }

        override fun onExerciseEventReceived(event: ExerciseEvent) {
            Timber.tag("HealthRepository").i("Exercise event received: $event")

            dataService._tempExerciseEvents.value = dataService._tempExerciseEvents.value + event
        }

        override fun onExerciseUpdateReceived(update: ExerciseUpdate) {
            Timber.tag("HealthRepository").i("Exercise update received: $update")
        }

        override fun onLapSummaryReceived(lapSummary: ExerciseLapSummary) {
        }

        override fun onRegistered() {
        }

        override fun onRegistrationFailed(throwable: Throwable) {
        }
    }

    fun initMeasure() {
        passiveMonitoringClient.setPassiveListenerCallback(
            passiveListenerConfig,
            passiveListenerCallback
        )

        CoroutineScope(Dispatchers.Default).launch {
            exerciseClient.startExercise(exerciseConfig)
        }
        exerciseClient.setUpdateCallback(exerciseCallback)
    }

    fun unregisterMeasure() {

    }
}