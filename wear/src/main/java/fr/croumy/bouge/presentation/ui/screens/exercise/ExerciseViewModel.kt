package fr.croumy.bouge.presentation.ui.screens.exercise

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.DataService
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    dataService: DataService
): ViewModel() {
    val accelerometer = dataService.accelerometerValue
}