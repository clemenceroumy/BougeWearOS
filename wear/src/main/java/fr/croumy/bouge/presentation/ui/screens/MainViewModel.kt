package fr.croumy.bouge.presentation.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.core.mocks.companionPlaceholder
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    companionService: CompanionService
): ViewModel() {
    val companion = companionService.myCompanion.stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = companionPlaceholder
    )
}