package fr.croumy.bouge.core.models.shop

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import java.util.UUID

interface IShopItem {
    val id: UUID
    val name: StringResource
    val description: String
    val price: Int
    val assetId: DrawableResource

    companion object {
        // ITEMS UUIDs
        //FOOD
        val FOOD_BREAD_UUID = UUID.fromString("6eced88c-5282-4bbc-bd3a-78379e9f16e2")
        val FOOD_EGG_UUID = UUID.fromString("1ac4e81a-f6c7-46d0-a003-ba0e48e409ba")
        val FOOD_ONIGIRI_UUID = UUID.fromString("60dd2a62-b731-49fe-8d9f-416c577da27e")
        val FOOD_DRINK_UUID = UUID.fromString("f1274e94-0b01-457f-9a6a-7c7c50e616ce")
        val FOOD_DESSERT_UUID = UUID.fromString("dea4387d-d7ad-4b11-82b0-a2d785399ddb")
        //BACKGROUND
        val PLAY_BALL_UUID = UUID.fromString("3f5e2d1a-9c4b-4e2a-8f7b-0a1b2c3d4e5f")
        val BACKGROUND_FOREST_UUID = UUID.fromString("a1c34aa0-4f90-47a1-a8d5-69fffc52f5e9")
        val BACKGROUND_OCEAN_UUID = UUID.fromString("7cb8b65a-ca66-48a5-bbd3-f8afbd9934f7")
        val BACKGROUND_DESERT_UUID = UUID.fromString("d1e3f3e4-2f4e-4c8a-9d3a-2e5f6b7c8d9e")
        val BACKGROUND_CITYROADSIDE_UUID = UUID.fromString("e2f4a5b6-c7d8-4e9f-8a0b-1c2d3e4f5a6b")
        val BACKGROUND_CITYROAD_UUID = UUID.fromString("f2ef9121-5271-4e3f-b63d-b6e3575e5286")
        val BACKGROUND_CITYSHOP_UUID = UUID.fromString("1b28631b-02d8-4813-ae35-dd6c6b633fdb")
        val BACKGROUND_MOUNTAINTREE_UUID = UUID.fromString("f3a4b5c6-d7e8-4f9a-0b1c-2d3e4f5a6b7c")
        val BACKGROUND_MOUNTAINBUSH_UUID = UUID.fromString("b4afeb45-49c0-4200-97f5-fc34ffa29827")
    }
}