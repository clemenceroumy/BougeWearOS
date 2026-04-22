package fr.croumy.bouge.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.DataService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataService: DataService,
): ViewModel() {
    val isWalking = dataService.isWalking
    val totalSteps = dataService.totalSteps
    val walks = dataService.currentWalk
}