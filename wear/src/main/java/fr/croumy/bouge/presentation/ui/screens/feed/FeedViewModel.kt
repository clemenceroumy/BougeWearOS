package fr.croumy.bouge.presentation.ui.screens.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.shop.food.FoodItem
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
    val allFoodItems = mutableStateOf(emptyList<Pair<FoodItem, Int>>())

    init {
        viewModelScope.launch {
            companion.value = companionService.myCompanion.first()
            allFoodItems.value = inventoryService.getAllFoodItems()
        }
    }

    fun feedCompanion(foodItem: FoodItem) {
        viewModelScope.launch {
            feedUseCase(FeedParams(foodItem = foodItem))
            allFoodItems.value = inventoryService.getAllFoodItems()
        }
    }
}