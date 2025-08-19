package fr.croumy.bouge.presentation.services

import android.os.SystemClock
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.usecases.RegisterExerciseParams
import fr.croumy.bouge.presentation.usecases.RegisterExerciseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.time.toKotlinDuration

@AndroidEntryPoint
class HealthService @Inject constructor(
    val dataService: DataService,
    val registerExerciseUseCase: RegisterExerciseUseCase
) : PassiveListenerService() {
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
            onDataReceived(dataPoints)
        }
    }

    init {
        // WHEN NO STEPS RECEIVED FOR A WHILE, REGISTER THE WALK
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            dataService.lastStepTime
                .debounce(Constants.TIME_GAP_BETWEEN_WALKS)
                .collect {
                    if (dataService.currentWalk.value > Constants.MINIMUM_STEPS_WALK) {
                        registerExerciseUseCase(
                            RegisterExerciseParams(
                                steps = dataService.currentWalk.value,
                                startTime = dataService.firstStepTime.value,
                                endTime = dataService.lastStepTime.value
                            )
                        )
                    }
                    dataService.setCurrentWalk(0)
                }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("HealthService created")
    }

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        super.onNewDataPointsReceived(dataPoints)
        onDataReceived(dataPoints)
    }

    fun onDataReceived(dataPoints: DataPointContainer) {
        if (dataPoints.dataTypes.contains(DataType.Companion.STEPS_DAILY)) {
            val dataPointStepDaily = dataPoints.getData(DataType.STEPS_DAILY).last()

            val totalStepsToday = dataPointStepDaily.value
            dataService.setTotalSteps(totalStepsToday.toInt())
        }

        if (dataPoints.dataTypes.contains(DataType.STEPS)) {
            dataService.setIsWalking(true)

            val bootInstant =
                Instant.ofEpochMilli(System.currentTimeMillis() - SystemClock.elapsedRealtime())

            val dataPointSteps = dataPoints.getData(DataType.STEPS)
            val dataPointStepsWithTime = dataPointSteps.map { dataPoint ->
                val stepTime = dataPoint.getEndInstant(bootInstant)
                Pair(stepTime, dataPoint)
            }

            dataPointStepsWithTime.forEachIndexed { index, pair ->
                val stepTime: Instant = pair.first
                val dataPoint = pair.second

                dataService.lastStepTime.value = ZonedDateTime.ofInstant(
                    stepTime,
                    ZoneId.systemDefault()
                )

                val previousStepInstant: Instant? =
                    dataPointStepsWithTime.getOrNull(index - 1)?.first
                val previousStepTime = Duration.between(
                    previousStepInstant ?: dataService.lastStepTime.value.toInstant(),
                    stepTime
                ).toKotlinDuration()

                if (previousStepTime < Constants.TIME_GAP_BETWEEN_WALKS) {
                    if(dataService.currentWalk.value == 0) dataService.firstStepTime.value = dataService.lastStepTime.value

                    dataService.setCurrentWalk(dataService.currentWalk.value + dataPoint.value.toInt())
                } else {
                    // IN CASE STEPS ARE RECEIVED IN THE SAME BATCH BUT WITH A GAP
                    if (dataService.currentWalk.value > Constants.MINIMUM_STEPS_WALK) {
                        registerExerciseUseCase(
                            RegisterExerciseParams(
                                steps = dataService.currentWalk.value,
                                startTime = dataService.firstStepTime.value,
                                endTime = dataService.lastStepTime.value
                            )
                        )
                    }
                    dataService.firstStepTime.value = dataService.lastStepTime.value
                    dataService.setCurrentWalk(dataPoint.value.toInt())
                }
            }
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