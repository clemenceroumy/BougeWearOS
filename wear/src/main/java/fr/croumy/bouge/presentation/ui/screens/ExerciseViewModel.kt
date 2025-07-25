package fr.croumy.bouge.presentation.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.repositories.HealthRepository
import fr.croumy.bouge.presentation.repositories.SensorRepository
import fr.croumy.bouge.presentation.services.DataService
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val dataService: DataService,
    private val sensorRepository: SensorRepository,
    private val healthRepository: HealthRepository
): ViewModel() {
    val accelerometerValue = dataService.accelerometerValue
    val heartRateValue = dataService.heartrateValue
    val isWalking = dataService.isWalking
    val totalSteps = dataService.totalSteps
    val tempExerciseEvents = dataService.tempExerciseEvents

    init {
        sensorRepository.initSensors()
        healthRepository.initMeasure()
    }

    override fun onCleared() {
        super.onCleared()

        sensorRepository.unregisterSensors()
        healthRepository.unregisterMeasure()
    }
}