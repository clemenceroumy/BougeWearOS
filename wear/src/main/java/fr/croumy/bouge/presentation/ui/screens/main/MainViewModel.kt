package fr.croumy.bouge.presentation.ui.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.DataService
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataService: DataService,
): ViewModel() {
    val accelerometerValue = dataService.accelerometerValue
    val heartRateValue = dataService.heartrateValue
    val isWalking = dataService.isWalking
    val totalSteps = dataService.totalSteps
    val walks = dataService.currentWalk
}