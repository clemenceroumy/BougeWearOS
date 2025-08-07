package fr.croumy.bouge.presentation.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.repositories.HealthRepository
import fr.croumy.bouge.presentation.repositories.SensorRepository
import fr.croumy.bouge.presentation.repositories.WalkRepository
import fr.croumy.bouge.presentation.services.DataService
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val dataService: DataService,
    private val sensorRepository: SensorRepository,
    private val healthRepository: HealthRepository,
    private val walkRepository: WalkRepository
): ViewModel() {
    val accelerometerValue = dataService.accelerometerValue
    val heartRateValue = dataService.heartrateValue
    val isWalking = dataService.isWalking
    val totalSteps = dataService.totalSteps
    val walks = dataService.currentWalk

    init {
        //sensorRepository.initSensors()
        healthRepository.initMeasure()
    }

    override fun onCleared() {
        super.onCleared()

        sensorRepository.unregisterSensors()
        healthRepository.unregisterMeasure()
    }
}