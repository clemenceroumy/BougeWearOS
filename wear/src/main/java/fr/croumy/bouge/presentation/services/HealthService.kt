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
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.data.entities.DailyStepsEntity
import fr.croumy.bouge.presentation.extensions.toYYYYMMDD
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.repositories.DailyStepsRepository
import fr.croumy.bouge.presentation.usecases.exercises.ConvertStepsToWalkUseCase
import fr.croumy.bouge.presentation.usecases.exercises.ConvertStepsToWalkUseCaseParams
import fr.croumy.bouge.presentation.usecases.exercises.RegisterExerciseParams
import fr.croumy.bouge.presentation.usecases.exercises.RegisterExerciseUseCase
import timber.log.Timber
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject
import kotlin.time.toKotlinDuration

@AndroidEntryPoint
class HealthService @Inject constructor() : PassiveListenerService() {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var dataService: DataService

    @Inject
    lateinit var dailyStepsRepository: DailyStepsRepository

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

        val notification = NotificationCompat
            .Builder(context, NotificationService.CHANNEL_ID_FOREGROUND)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        ServiceCompat.startForeground(
            this,
            20,
            notification.build(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH
        )
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
            dailyStepsRepository.insert(DailyStepsEntity(totalStepsTime.toYYYYMMDD(), dataPointStepDaily.value.toInt()))
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
        val passiveMonitoringClient = HealthServices
            .getClient(context)
            .passiveMonitoringClient

        passiveMonitoringClient.setPassiveListenerCallback(
            passiveListenerConfig,
            passiveListenerCallback
        )

        val serviceIntent = Intent(context, HealthService::class.java)
        context.startForegroundService(serviceIntent)
    }
}