package fr.croumy.bouge.presentation.services

import android.os.CountDownTimer
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
import kotlinx.coroutines.Job
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
class HealthService @Inject constructor() : PassiveListenerService() {
    @Inject
    lateinit var dataService: DataService
    @Inject
    lateinit var registerExerciseUseCase: RegisterExerciseUseCase

    val healthClient = HealthServices.getClient(MainActivity.Companion.context)
    val passiveMonitoringClient = healthClient.passiveMonitoringClient
    val countdown = object : CountDownTimer(Constants.TIME_GAP_BETWEEN_WALKS.inWholeMilliseconds, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            Timber.i("Countdown finished, current walk of ${dataService.currentWalk.value} steps is over")
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

    override fun onCreate() {
        super.onCreate()
        Timber.i("HealthService created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("HealthService destroyed")
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
            countdown.cancel()
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
                    if (dataService.currentWalk.value == 0) dataService.firstStepTime.value =
                        dataService.lastStepTime.value

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
            countdown.start()
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