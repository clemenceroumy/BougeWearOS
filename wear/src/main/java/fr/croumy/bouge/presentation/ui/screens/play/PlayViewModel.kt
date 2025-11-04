package fr.croumy.bouge.presentation.ui.screens.play

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    companionService: CompanionService
): ViewModel() {
    val companion = mutableStateOf<Companion?>(null)

    init {
        viewModelScope.launch {
            companion.value = companionService.myCompanion.first()
        }
    }
}