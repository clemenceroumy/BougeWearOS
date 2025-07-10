package fr.croumy.bouge.presentation.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.SensorRepository
import fr.croumy.bouge.presentation.services.SensorService
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val sensorService: SensorService,
    private val sensorRepository: SensorRepository
): ViewModel() {
    val accelerometerValue = sensorService.accelerometerValue
    val heartRateValue = sensorService.heartrateValue

    init {
        sensorRepository.initSensors()
    }

    override fun onCleared() {
        super.onCleared()
        sensorRepository.unregisterSensors()
    }
}