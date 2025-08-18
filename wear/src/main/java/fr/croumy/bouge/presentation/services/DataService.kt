package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.models.AccelerometerValue
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.usecases.RegisterExerciseParams
import fr.croumy.bouge.presentation.usecases.RegisterExerciseUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(FlowPreview::class)
@Singleton
class DataService @Inject constructor(
    val registerExerciseUseCase: RegisterExerciseUseCase
) {
    private val _accelerometerValue = MutableStateFlow(AccelerometerValue())
    val accelerometerValue = _accelerometerValue.asStateFlow()

    private val _heartrateValue = MutableStateFlow(0)
    val heartrateValue = _heartrateValue.asStateFlow()

    val firstStepTime = MutableStateFlow(ZonedDateTime.now())
    val lastStepTime = MutableStateFlow(ZonedDateTime.now())
    private val _isWalking = MutableStateFlow(false)
    fun setIsWalking(value: Boolean) { _isWalking.value = value }
    val isWalking = _isWalking.asStateFlow()

    private val _currentWalk = MutableStateFlow(0)
    fun setCurrentWalk(value: Int) { _currentWalk.value = value }
    val currentWalk = _currentWalk.asStateFlow()

    private val _totalSteps = MutableStateFlow(0)
    fun setTotalSteps(value: Int) { _totalSteps.value = value }
    val totalSteps = _totalSteps.asStateFlow()


    init {
        MainScope().launch {
            currentWalk
                .filter { steps -> steps == 1 }
                .collect {
                    firstStepTime.value = ZonedDateTime.now()
                }
        }

        MainScope().launch {
            lastStepTime
                .debounce(Constants.TIME_STOPPED_WALKING)
                .collect { latestTime ->
                    _isWalking.value = false
                }

        }

        MainScope().launch {
            _isWalking
                .debounce(Constants.TIME_GAP_BETWEEN_WALKS)
                .collect {
                    if (_currentWalk.value > Constants.MINIMUM_STEPS_WALK) {
                        registerExerciseUseCase(
                            RegisterExerciseParams(
                                steps = _currentWalk.value,
                                startTime = firstStepTime.value,
                                endTime = lastStepTime.value
                            )
                        )
                    }
                    _currentWalk.value = 0
                }
        }
    }
}