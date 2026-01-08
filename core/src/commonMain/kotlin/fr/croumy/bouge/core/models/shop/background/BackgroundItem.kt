package fr.croumy.bouge.core.models.shop.background

import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_city
import bouge.core.generated.resources.background_forest
import bouge.core.generated.resources.background_mountain
import bouge.core.generated.resources.background_ocean
import bouge.core.generated.resources.background_space
import bouge.core.generated.resources.shop_background_city
import bouge.core.generated.resources.shop_background_forest
import bouge.core.generated.resources.shop_background_mountain
import bouge.core.generated.resources.shop_background_ocean
import bouge.core.generated.resources.shop_background_space
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

    object Mountain: BackgroundItem(
        id = IShopItem.BACKGROUND_MOUNTAIN_UUID,
        name = Res.string.shop_background_mountain,
        description = "",
        price = 200,
        assetId = Res.drawable.background_mountain
    )

    object Ocean: BackgroundItem(
        id = IShopItem.BACKGROUND_OCEAN_UUID,
        name = Res.string.shop_background_ocean,
        description = "",
        price = 200,
        assetId = Res.drawable.background_ocean
    )

    object City: BackgroundItem(
        id = IShopItem.BACKGROUND_CITY_UUID,
        name = Res.string.shop_background_city,
        description = "",
        price = 200,
        assetId = Res.drawable.background_city
    )

    object Space: BackgroundItem(
        id = IShopItem.BACKGROUND_SPACE_UUID,
        name = Res.string.shop_background_space,
        description = "",
        price = 500,
        assetId = Res.drawable.background_space
    )

    companion object {
        val allBackgrounds = listOf(Forest, Mountain, Ocean, City, Space)
        fun fromId(id: UUID): BackgroundItem? = allBackgrounds.firstOrNull { it.id == id }
    }
}
