package fr.croumy.bouge.presentation.ui.screens.historyCompanion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.CompanionService
import javax.inject.Inject

@HiltViewModel
class HistoryCompanionViewModel @Inject constructor(
    companionService: CompanionService
): ViewModel() {
    val companions = companionService.getDeadCompanions()
}