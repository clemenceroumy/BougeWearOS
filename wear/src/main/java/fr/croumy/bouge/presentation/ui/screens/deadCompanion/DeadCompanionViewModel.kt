package fr.croumy.bouge.presentation.ui.screens.deadCompanion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.CompanionService
import javax.inject.Inject

@HiltViewModel
class DeadCompanionViewModel @Inject constructor(
    companionService: CompanionService
): ViewModel() {
    val deadCompanion = companionService.getLastestDeadCompanion()!!
}