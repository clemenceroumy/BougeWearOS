package fr.croumy.bouge.presentation.ui.screens.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.InventoryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    val inventoryService: InventoryService,
    val companionService: CompanionService,
): ViewModel() {
    val companion = mutableStateOf<Companion?>(null)
    val allFoodItems = inventoryService.getAllFoodItems()

    init {
        viewModelScope.launch {
            companion.value = companionService.myCompanion.first()
        }
    }

    fun feedCompanion(foodItem: FoodItem) {

        //REMOVE 1 ITEM IN DB
        // APPLY ITEM STATS
    }
}