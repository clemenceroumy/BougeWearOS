package fr.croumy.bouge.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.DataService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataService: DataService,
    private val companionService: CompanionService
): ViewModel() {
    val isWalking = dataService.isWalking
    val totalSteps = dataService.totalSteps
    val walks = dataService.currentWalk

    val companion = companionService.myCompanion.stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
}