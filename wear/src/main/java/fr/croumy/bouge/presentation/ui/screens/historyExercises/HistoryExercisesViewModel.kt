package fr.croumy.bouge.presentation.ui.screens.historyExercises

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.models.exercise.Walk
import fr.croumy.bouge.presentation.repositories.WalkRepository
import javax.inject.Inject

@HiltViewModel
class HistoryExercisesViewModel @Inject constructor(
    walkRepository: WalkRepository
): ViewModel() {
    val walks = mutableStateOf(emptyList<Walk>())
    val walksByDay get() = walks.value.groupBy {
        it.startDateTime.toLocalDate()
    }

    init {
        walks.value = walkRepository.getAllWalks()
    }
}