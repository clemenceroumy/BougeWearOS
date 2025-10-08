package fr.croumy.bouge.presentation.models.shop.food

import androidx.annotation.DrawableRes
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.models.companion.StatsType
import fr.croumy.bouge.presentation.models.shop.IShopItem

abstract class FoodItem(
    override val id: String,
    override val name: Int,
    override val description: String,
    override val price: Int,
    val statsBoost: Map<StatsType, Float>
) : IShopItem {
    abstract val assetId: Int

    object Bread: FoodItem(
        id = IShopItem.FOOD_BREAD_UUID,
        name = R.string.shop_food_bread,
        description = "",
        price = 5,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.baguette
    }

    object Egg: FoodItem(
        id = IShopItem.FOOD_EGG_UUID,
        name = R.string.shop_food_egg,
        description = "",
        price = 10,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.75f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.egg
    }

    object Onigiri: FoodItem(
        id = IShopItem.FOOD_ONIGIRI_UUID,
        name = R.string.shop_food_onigiri,
        description = "",
        price = 12,
        statsBoost = mapOf(StatsType.HUNGRINESS to 1f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.onigiri
    }

    object Drink: FoodItem(
        id = IShopItem.FOOD_DRINK_UUID,
        name = R.string.shop_food_drink,
        description = "",
        price = 5,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.drink
    }

    object Dessert: FoodItem(
        id = IShopItem.FOOD_DESSERT_UUID,
        name = R.string.shop_food_dessert,
        description = "",
        price = 12,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.5f, StatsType.HAPPINESS to 0.25f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.cake
    }

    companion object {
        val allFood = listOf(Bread, Egg, Onigiri, Drink, Dessert)
        fun fromId(id: String): FoodItem? = allFood.firstOrNull { it.id == id }
    }
}
