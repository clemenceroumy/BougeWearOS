package fr.croumy.bouge.presentation.services

import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.usecases.exercises.ConvertStepsToWalkUseCase
import fr.croumy.bouge.presentation.usecases.exercises.ConvertStepsToWalkUseCaseParams
import fr.croumy.bouge.presentation.usecases.exercises.RegisterExerciseParams
import fr.croumy.bouge.presentation.usecases.exercises.RegisterExerciseUseCase
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class HealthService @Inject constructor() : PassiveListenerService() {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var dataService: DataService

    @Inject
    lateinit var dailyStepsService: DailyStepsService

    @Inject
    lateinit var notificationService: NotificationService

    @Inject
    lateinit var registerExerciseUseCase: RegisterExerciseUseCase

    @Inject
    lateinit var convertStepsToWalkUseCase: ConvertStepsToWalkUseCase

    val countdown = object : CountDownTimer(Constants.TIME_GAP_BETWEEN_WALKS.inWholeMilliseconds, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
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

        notificationService.hideRebootNotification()

        initServiceForeground()

        startListeningData()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun onDataReceived(dataPoints: DataPointContainer) {
        if (dataPoints.dataTypes.contains(DataType.Companion.STEPS_DAILY)) {
            val dataPointStepDaily = dataPoints.getData(DataType.STEPS_DAILY).last()

            val bootInstant = Instant.ofEpochMilli(System.currentTimeMillis() - SystemClock.elapsedRealtime())
            val totalStepsTime = dataPointStepDaily.getEndInstant(bootInstant)

            dataService.setTotalSteps(dataPointStepDaily.value.toInt())
            dailyStepsService.insert(totalStepsTime, dataPointStepDaily.value.toInt())
        }

        if (dataPoints.dataTypes.contains(DataType.STEPS)) {
            countdown.cancel()

            val dataPointSteps = dataPoints.getData(DataType.STEPS)
            convertStepsToWalkUseCase(
                ConvertStepsToWalkUseCaseParams(
                    dataPoints = dataPointSteps
                )
            )

            countdown.start()
        }
    }

    fun initService() {
        val serviceIntent = Intent(context, HealthService::class.java)
        context.startForegroundService(serviceIntent)

        // NEXT, WAIT FOR THE SERVICE TO BE SET AS FOREGROUND BY initServiceForeground()
    }

    private fun initServiceForeground() {
        val notificationForeground = NotificationCompat
            .Builder(context, NotificationService.CHANNEL_ID_FOREGROUND)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        ServiceCompat.startForeground(
            this,
            NotificationService.NOTIFICATION_FOREGROUND_HEALTH_SERVICE_ID,
            notificationForeground,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH
        )

        // NEXT, WE NEED TO START LISTENING DATA VIA startListeningData()
    }

    private fun startListeningData() {
        val passiveMonitoringClient = HealthServices
            .getClient(context)
            .passiveMonitoringClient

        passiveMonitoringClient.setPassiveListenerCallback(
            passiveListenerConfig,
            passiveListenerCallback
        )
    }
}