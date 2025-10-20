package fr.croumy.bouge.presentation.services

import android.content.Context
import fr.croumy.bouge.presentation.models.AccelerometerValue
import fr.croumy.bouge.presentation.constants.Constants
import fr.croumy.bouge.presentation.usecases.exercises.RegisterExerciseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.io.File
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(FlowPreview::class)
@Singleton
class DataService @Inject constructor(
    val registerExerciseUseCase: RegisterExerciseUseCase,
    val context: Context
) {
    val accelerometerValue = MutableStateFlow(AccelerometerValue(0f, 0f, 0f))
    val firstStepTime = MutableStateFlow(ZonedDateTime.now())
    val lastStepTime = MutableStateFlow(ZonedDateTime.now())
    private val _isWalking = MutableStateFlow(false)
    fun setIsWalking(value: Boolean) {
        _isWalking.value = value
    }

    val isWalking = _isWalking.asStateFlow()

    private val _currentWalk = MutableStateFlow(0)
    fun setCurrentWalk(value: Int) {
        _currentWalk.value = value
    }

    val currentWalk = _currentWalk.asStateFlow()

    private val _totalSteps = MutableStateFlow(0)
    fun setTotalSteps(value: Int) {
        _totalSteps.value = value
    }

    val totalSteps = _totalSteps.asStateFlow()


    init {
        checkAndUpdateCurrentWalkStatus()
    }

    fun checkAndUpdateCurrentWalkStatus() {
        val backgroundScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

        backgroundScope.launch {
            lastStepTime
                .debounce(Constants.TIME_STOPPED_WALKING)
                .collect { latestTime ->
                    _isWalking.value = false
                }

        }
    }

    fun retrieveGyroSensor(data: AccelerometerValue) {
        val file = File(context.filesDir, "sensor_data.txt")
        file.appendText("${data.x},${data.y},${data.z},${System.currentTimeMillis()}\n")

        accelerometerValue.value = data
    }
}