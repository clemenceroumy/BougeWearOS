package fr.croumy.bouge.presentation.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.models.companion.Stats
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompanionStatsViewModel @Inject constructor(
    companionService: CompanionService
) : ViewModel() {
    val stats: StateFlow<Stats?> = companionService
        .getStats()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = null
        )
}