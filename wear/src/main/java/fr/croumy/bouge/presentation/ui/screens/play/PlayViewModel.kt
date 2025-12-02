package fr.croumy.bouge.presentation.ui.screens.play

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.shop.toy.ToyItem
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.usecases.companion.PlayParams
import fr.croumy.bouge.presentation.usecases.companion.PlayUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    companionService: CompanionService,
    val playUseCase: PlayUseCase
): ViewModel() {
    val companion = mutableStateOf<Companion?>(null)

    init {
        viewModelScope.launch {
            companion.value = companionService.myCompanion.first()
        }
    }

    fun play(toy: ToyItem) {
        playUseCase(PlayParams(playItem = toy))
    }
}