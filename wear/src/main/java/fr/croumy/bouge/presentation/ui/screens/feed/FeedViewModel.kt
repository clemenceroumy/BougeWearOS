package fr.croumy.bouge.presentation.ui.screens.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.InventoryService
import fr.croumy.bouge.presentation.usecases.companion.FeedParams
import fr.croumy.bouge.presentation.usecases.companion.FeedUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    val inventoryService: InventoryService,
    val companionService: CompanionService,
    val feedUseCase: FeedUseCase
): ViewModel() {
    val companion = mutableStateOf<Companion?>(null)
    val allFoodItems = mutableStateOf(inventoryService.getAllFoodItems())

    init {
        viewModelScope.launch {
            companion.value = companionService.myCompanion.first()
        }
    }

    fun feedCompanion(foodItem: FoodItem) {
        feedUseCase(FeedParams(foodItem = foodItem))
        allFoodItems.value = inventoryService.getAllFoodItems()
    }
}