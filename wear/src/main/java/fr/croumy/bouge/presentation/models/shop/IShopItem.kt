package fr.croumy.bouge.presentation.models.shop

import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import java.util.UUID

interface IShopItem {
    val id: UUID
    val name: Int
    val description: String
    val price: Int
    val assetId: Int

    companion object {
        val allShopItems = FoodItem.allFood

        // ITEMS UUIDs
        //FOOD
        val FOOD_BREAD_UUID = UUID.fromString("6eced88c-5282-4bbc-bd3a-78379e9f16e2")
        val FOOD_EGG_UUID = UUID.fromString("1ac4e81a-f6c7-46d0-a003-ba0e48e409ba")
        val FOOD_ONIGIRI_UUID = UUID.fromString("60dd2a62-b731-49fe-8d9f-416c577da27e")
        val FOOD_DRINK_UUID = UUID.fromString("f1274e94-0b01-457f-9a6a-7c7c50e616ce")
        val FOOD_DESSERT_UUID = UUID.fromString("dea4387d-d7ad-4b11-82b0-a2d785399ddb")
        //BACKGROUND
        val BACKGROUND_FOREST_UUID = UUID.fromString("a1c34aa0-4f90-47a1-a8d5-69fffc52f5e9")
        val BACKGROUND_OCEAN_UUID = UUID.fromString("7cb8b65a-ca66-48a5-bbd3-f8afbd9934f7")
        val BACKGROUND_SPACE_UUID = UUID.fromString("d1e3f3e4-2f4e-4c8a-9d3a-2e5f6b7c8d9e")
        val BACKGROUND_CITY_UUID = UUID.fromString("e2f4a5b6-c7d8-4e9f-8a0b-1c2d3e4f5a6b")
        val BACKGROUND_MOUNTAIN_UUID = UUID.fromString("f3a4b5c6-d7e8-4f9a-0b1c-2d3e4f5a6b7c")
    }
}