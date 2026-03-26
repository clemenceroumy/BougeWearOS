package fr.croumy.bouge.presentation.ui.screens.background

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.InventoryService
import kotlinx.coroutines.flow.MutableStateFlow
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
    val allBackgroundItems = mutableStateOf(emptyList<BackgroundItem>())

    init {
        viewModelScope.launch {
            allBackgroundItems.value = inventoryService.getAllBackgroundItems()
        }

        viewModelScope.launch {
            companionService.myCompanion.collect {
                companion.value = it
            }
        }
    }

    fun selectBackground(itemId: UUID) {
        viewModelScope.launch {
            companionService.selectBackground(itemId)
        }
    }
}