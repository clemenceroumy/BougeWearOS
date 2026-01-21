package fr.croumy.bouge.presentation.ui.screens.pickCompanion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.core.models.companion.CompanionType
import fr.croumy.bouge.presentation.services.CompanionService
import javax.inject.Inject

@HiltViewModel
class PickCompanionViewModel @Inject constructor(
    val companionService: CompanionService
): ViewModel() {
    suspend fun selectCompanion(companionType: CompanionType, customName: String) {
        companionService.selectCompanion(companionType, customName)
    }
}