package fr.croumy.bouge.services

import androidx.compose.runtime.mutableStateOf
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.shop.food.FoodItem

class CompanionService {
    val currentCompanion = mutableStateOf<Companion?>(null)
    val currentDrops = mutableStateOf<List<FoodItem>>(emptyList())
}