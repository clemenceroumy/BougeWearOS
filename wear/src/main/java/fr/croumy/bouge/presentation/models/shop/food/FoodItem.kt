package fr.croumy.bouge.presentation.models.shop.food

import androidx.annotation.DrawableRes
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.models.companion.StatsType
import fr.croumy.bouge.presentation.models.shop.IShopItem
import java.util.UUID

abstract class FoodItem(
    override val id: UUID,
    override val name: Int,
    override val description: String,
    override val price: Int,
    @DrawableRes override val assetId: Int,
    val statsBoost: Map<StatsType, Float>
) : IShopItem {

    object Bread: FoodItem(
        id = IShopItem.FOOD_BREAD_UUID,
        name = R.string.shop_food_bread,
        description = "",
        price = 5,
        assetId = R.drawable.baguette,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f)
    )

    object Egg: FoodItem(
        id = IShopItem.FOOD_EGG_UUID,
        name = R.string.shop_food_egg,
        description = "",
        price = 10,
        assetId = R.drawable.egg,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.75f)
    )

    object Onigiri: FoodItem(
        id = IShopItem.FOOD_ONIGIRI_UUID,
        name = R.string.shop_food_onigiri,
        description = "",
        price = 12,
        assetId = R.drawable.onigiri,
        statsBoost = mapOf(StatsType.HUNGRINESS to 1f)
    )

    object Drink: FoodItem(
        id = IShopItem.FOOD_DRINK_UUID,
        name = R.string.shop_food_drink,
        description = "",
        price = 5,
        assetId = R.drawable.drink,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f)
    )

    object Dessert: FoodItem(
        id = IShopItem.FOOD_DESSERT_UUID,
        name = R.string.shop_food_dessert,
        description = "",
        price = 12,
        assetId = R.drawable.cake,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.5f, StatsType.HAPPINESS to 0.25f)
    )

    companion object {
        val allFood = listOf(Bread, Egg, Onigiri, Drink, Dessert)
        fun fromId(id: UUID): FoodItem? = allFood.firstOrNull { it.id == id }
    }
}
