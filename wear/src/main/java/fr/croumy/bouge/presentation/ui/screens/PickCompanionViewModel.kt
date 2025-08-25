package fr.croumy.bouge.presentation.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.models.companion.CompanionType
import fr.croumy.bouge.presentation.services.CompanionService
import javax.inject.Inject

@HiltViewModel
class PickCompanionViewModel @Inject constructor(
    val companionService: CompanionService
): ViewModel() {
    fun selectCompanion(companionType: CompanionType) {
        companionService.selectCompanion(companionType)
    }
}