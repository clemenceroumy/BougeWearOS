package fr.croumy.bouge.core.models.shop.food

import bouge.core.generated.resources.Res
import bouge.core.generated.resources.baguette
import bouge.core.generated.resources.cake
import bouge.core.generated.resources.drink
import bouge.core.generated.resources.egg
import bouge.core.generated.resources.onigiri
import bouge.core.generated.resources.pizza
import bouge.core.generated.resources.shop_food_bread
import bouge.core.generated.resources.shop_food_dessert
import bouge.core.generated.resources.shop_food_drink
import bouge.core.generated.resources.shop_food_egg
import bouge.core.generated.resources.shop_food_onigiri
import bouge.core.generated.resources.shop_food_pizza
import bouge.core.generated.resources.shop_food_soup
import bouge.core.generated.resources.soup
import fr.croumy.bouge.core.models.companion.StatsType
import fr.croumy.bouge.core.models.shop.IShopItem
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import java.util.UUID

abstract class FoodItem(
    override val id: UUID,
    override val name: StringResource,
    override val description: String,
    override val price: Int,
    override val assetId: DrawableResource,
    val statsBoost: Map<StatsType, Float>,
    val dropChance: Int // 1/dropChance
) : IShopItem {

    object Bread: FoodItem(
        id = IShopItem.FOOD_BREAD_UUID,
        name = Res.string.shop_food_bread,
        description = "",
        price = 5,
        assetId = Res.drawable.baguette,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f),
        dropChance = 10
    )

    object Egg: FoodItem(
        id = IShopItem.FOOD_EGG_UUID,
        name = Res.string.shop_food_egg,
        description = "",
        price = 10,
        assetId = Res.drawable.egg,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.75f),
        dropChance = 30
    )

    object Onigiri: FoodItem(
        id = IShopItem.FOOD_ONIGIRI_UUID,
        name = Res.string.shop_food_onigiri,
        description = "",
        price = 12,
        assetId = Res.drawable.onigiri,
        statsBoost = mapOf(StatsType.HUNGRINESS to 1f),
        dropChance = 50
    )

    object Drink: FoodItem(
        id = IShopItem.FOOD_DRINK_UUID,
        name = Res.string.shop_food_drink,
        description = "",
        price = 8,
        assetId = Res.drawable.drink,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.25f, StatsType.HAPPINESS to 0.25f),
        dropChance = 10
    )

    object Dessert: FoodItem(
        id = IShopItem.FOOD_DESSERT_UUID,
        name = Res.string.shop_food_dessert,
        description = "",
        price = 12,
        assetId = Res.drawable.cake,
        statsBoost = mapOf(StatsType.HUNGRINESS to 0.75f, StatsType.HAPPINESS to 0.25f),
        dropChance = 50
    )

    object Pizza: FoodItem(
        id = IShopItem.FOOD_PIZZA_UUID,
        name = Res.string.shop_food_pizza,
        description = "",
        price = 18,
        assetId = Res.drawable.pizza,
        statsBoost = mapOf(StatsType.HUNGRINESS to 2f),
        dropChance = 80
    )

    object Soup: FoodItem(
        id = IShopItem.FOOD_SOUP_UUID,
        name = Res.string.shop_food_soup,
        description = "",
        price = 25,
        assetId = Res.drawable.soup,
        statsBoost = mapOf(StatsType.HUNGRINESS to 3f),
        dropChance = 100
    )

    companion object {
        val allFood by lazy { listOf(Bread, Egg, Onigiri, Drink, Dessert, Pizza, Soup) }
        fun fromId(id: UUID): FoodItem? = allFood.firstOrNull { it.id == id }
    }
}
