package fr.croumy.bouge.presentation.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.repositories.SensorRepository
import fr.croumy.bouge.presentation.services.SensorService
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val sensorService: SensorService,
    private val sensorRepository: SensorRepository
): ViewModel() {
    val accelerometerValue = sensorService.accelerometerValue
    val heartRateValue = sensorService.heartrateValue
    val isWalking = sensorService.isWalking

    init {
        sensorRepository.initSensors()
    }

    override fun onCleared() {
        super.onCleared()
        sensorRepository.unregisterSensors()
    }
}