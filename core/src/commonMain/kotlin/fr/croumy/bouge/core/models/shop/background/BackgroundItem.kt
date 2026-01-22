package fr.croumy.bouge.core.models.shop.background

import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_city_road
import bouge.core.generated.resources.background_city_roadside
import bouge.core.generated.resources.background_city_shop
import bouge.core.generated.resources.background_desert
import bouge.core.generated.resources.background_forest
import bouge.core.generated.resources.background_mountain_bush
import bouge.core.generated.resources.background_mountain_tree
import bouge.core.generated.resources.background_ocean
import bouge.core.generated.resources.shop_background_city
import bouge.core.generated.resources.shop_background_desert
import bouge.core.generated.resources.shop_background_forest
import bouge.core.generated.resources.shop_background_mountain
import bouge.core.generated.resources.shop_background_ocean
import fr.croumy.bouge.core.models.shop.IShopItem
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import java.util.UUID

abstract class BackgroundItem(
    override val id: UUID,
    override val name: StringResource,
    override val description: String,
    override val price: Int,
    override val assetId: DrawableResource
) : IShopItem {
    object Forest: BackgroundItem(
        id = IShopItem.BACKGROUND_FOREST_UUID,
        name = Res.string.shop_background_forest,
        description = "",
        price = 50,
        assetId = Res.drawable.background_forest
    )

    object MountainTree: BackgroundItem(
        id = IShopItem.BACKGROUND_MOUNTAINTREE_UUID,
        name = Res.string.shop_background_mountain,
        description = "",
        price = 200,
        assetId = Res.drawable.background_mountain_tree
    )

    object MountainBush: BackgroundItem(
        id = IShopItem.BACKGROUND_MOUNTAINBUSH_UUID,
        name = Res.string.shop_background_mountain,
        description = "",
        price = 200,
        assetId = Res.drawable.background_mountain_bush
    )

    object Ocean: BackgroundItem(
        id = IShopItem.BACKGROUND_OCEAN_UUID,
        name = Res.string.shop_background_ocean,
        description = "",
        price = 200,
        assetId = Res.drawable.background_ocean
    )

    object CityRoadside: BackgroundItem(
        id = IShopItem.BACKGROUND_CITYROADSIDE_UUID,
        name = Res.string.shop_background_city,
        description = "",
        price = 200,
        assetId = Res.drawable.background_city_roadside
    )

    object CityRoad: BackgroundItem(
        id = IShopItem.BACKGROUND_CITYROAD_UUID,
        name = Res.string.shop_background_city,
        description = "",
        price = 200,
        assetId = Res.drawable.background_city_road
    )

    object CityShop: BackgroundItem(
        id = IShopItem.BACKGROUND_CITYSHOP_UUID,
        name = Res.string.shop_background_city,
        description = "",
        price = 200,
        assetId = Res.drawable.background_city_shop
    )

    object Desert: BackgroundItem(
        id = IShopItem.BACKGROUND_DESERT_UUID,
        name = Res.string.shop_background_desert,
        description = "",
        price = 500,
        assetId = Res.drawable.background_desert
    )

    companion object {
        val allBackgrounds = listOf(Forest, MountainTree, MountainBush, Ocean, CityRoad, CityRoadside, CityShop, Desert)
        fun fromId(id: UUID): BackgroundItem? = allBackgrounds.firstOrNull { it.id == id }
    }
}
