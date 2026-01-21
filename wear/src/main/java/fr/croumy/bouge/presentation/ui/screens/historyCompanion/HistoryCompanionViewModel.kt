package fr.croumy.bouge.presentation.ui.screens.historyCompanion

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryCompanionViewModel @Inject constructor(
    companionService: CompanionService
): ViewModel() {
    val companions = mutableStateOf(emptyList<Companion>())

    init {
        viewModelScope.launch {
            companions.value = companionService.getDeadCompanions()
        }
    }
}