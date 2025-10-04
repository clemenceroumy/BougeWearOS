package fr.croumy.bouge.presentation.models.shop.food

import androidx.annotation.DrawableRes
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.models.companion.StatsType
import fr.croumy.bouge.presentation.models.shop.IShopItem
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class FoodItem(
    override val id: Int,
    override val name: Int,
    override val description: String,
    override val price: Int,
    override val statsBoost: Map<StatsType, Float>
) : IShopItem {
    abstract val assetId: Int

    object Bread: FoodItem(
        id = 1,
        name = R.string.shop_food_bread,
        description = "",
        price = 5,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.baguette
    }

    object Egg: FoodItem(
        id = 2,
        name = R.string.shop_food_egg,
        description = "",
        price = 10,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.75f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.egg
    }

    object Onigiri: FoodItem(
        id = 3,
        name = R.string.shop_food_onigiri,
        description = "",
        price = 12,
        statsBoost = mapOf(StatsType.HUNGRINESS to 1f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.onigiri
    }

    object Drink: FoodItem(
        id = 4,
        name = R.string.shop_food_drink,
        description = "",
        price = 5,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.drink
    }

    object Dessert: FoodItem(
        id = 5,
        name = R.string.shop_food_dessert,
        description = "",
        price = 12,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.5f, StatsType.HAPPINESS to 0.25f)
    ) {
        @DrawableRes override val assetId: Int = R.drawable.cake
    }

    companion object {
        val allFood = listOf(Bread, Egg, Onigiri, Drink, Dessert)
        fun fromId(id: Int): FoodItem? = allFood.firstOrNull { it.id == id }
    }
}
