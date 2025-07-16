package fr.croumy.bouge.presentation.services

import android.util.Log
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.models.AccelerometerValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
@Singleton
class SensorService @Inject constructor() {
    val _accelerometerValue = MutableStateFlow(AccelerometerValue())
    val accelerometerValue = _accelerometerValue.asStateFlow()

    val _heartrateValue = MutableStateFlow(0)
    val heartrateValue = _heartrateValue.asStateFlow()

    val lastStepTime = MutableStateFlow(System.currentTimeMillis())
    val _isWalking = MutableStateFlow(false)
    val isWalking = _isWalking.asStateFlow()

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