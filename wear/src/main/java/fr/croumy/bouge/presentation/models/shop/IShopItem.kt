package fr.croumy.bouge.presentation.models.shop

import fr.croumy.bouge.presentation.models.companion.StatsType
import fr.croumy.bouge.presentation.models.shop.food.FoodItem

interface IShopItem {
    val id: Int
    val name: Int
    val description: String
    val price: Int
    val statsBoost: Map<StatsType, Float>

    companion object {
        val allShopItems = FoodItem.allFood
    }
}