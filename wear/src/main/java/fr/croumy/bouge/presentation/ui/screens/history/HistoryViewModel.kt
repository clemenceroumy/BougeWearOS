package fr.croumy.bouge.presentation.ui.screens.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.data.entities.WalkEntity
import fr.croumy.bouge.presentation.models.Walk
import fr.croumy.bouge.presentation.repositories.WalkRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    walkRepository: WalkRepository
): ViewModel() {
    val walks = mutableStateOf(emptyList<Walk>())

    init {
        walks.value = walkRepository.getAllWalks()
    }
}