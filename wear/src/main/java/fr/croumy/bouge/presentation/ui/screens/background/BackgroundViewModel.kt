package fr.croumy.bouge.presentation.ui.screens.background

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.InventoryService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BackgroundViewModel @Inject constructor(
    val inventoryService: InventoryService,
    val companionService: CompanionService,
): ViewModel() {
    val companion = mutableStateOf<Companion?>(null)
    val allBackgroundItems = mutableStateOf(inventoryService.getAllBackgroundItems())

    init {
        viewModelScope.launch {
            companion.value = companionService.myCompanion.first()
        }
    }

    fun selectBackground(itemId: UUID) {
        companionService.selectBackground(itemId)
    }
}