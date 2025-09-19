package fr.croumy.bouge.presentation.ui.screens.feed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.InventoryService
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    val inventoryService: InventoryService
): ViewModel() {
    val allFoodItems = inventoryService.getAllFoodItems()
}