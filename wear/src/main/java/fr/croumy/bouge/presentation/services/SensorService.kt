package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.models.AccelerometerValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorService @Inject constructor() {
    val _accelerometerValue = MutableStateFlow(AccelerometerValue())
    val accelerometerValue = _accelerometerValue.asStateFlow()

    val _heartrateValue = MutableStateFlow(0)
    val heartrateValue = _heartrateValue.asStateFlow()
}