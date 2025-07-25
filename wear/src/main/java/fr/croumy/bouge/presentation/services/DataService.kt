package fr.croumy.bouge.presentation.services

import androidx.health.services.client.data.ExerciseEvent
import fr.croumy.bouge.presentation.models.AccelerometerValue
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
@Singleton
class DataService @Inject constructor() {
    val _accelerometerValue = MutableStateFlow(AccelerometerValue())
    val accelerometerValue = _accelerometerValue.asStateFlow()

    val _heartrateValue = MutableStateFlow(0)
    val heartrateValue = _heartrateValue.asStateFlow()

    val lastStepTime = MutableStateFlow(System.currentTimeMillis())
    val _isWalking = MutableStateFlow(false)
    val isWalking = _isWalking.asStateFlow()

    val _totalSteps = MutableStateFlow(0)
    val totalSteps = _totalSteps.asStateFlow()

    val _tempExerciseEvents = MutableStateFlow(emptyList<ExerciseEvent>())
    val tempExerciseEvents = _tempExerciseEvents.asStateFlow()

    init {
        MainScope().launch {
            lastStepTime
                .debounce(2.seconds)
                .collect { latestTime ->
                    _isWalking.value = false
                }
        }
    }
}