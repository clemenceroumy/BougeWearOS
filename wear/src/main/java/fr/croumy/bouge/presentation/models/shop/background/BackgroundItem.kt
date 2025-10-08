package fr.croumy.bouge.presentation.models.shop.background

import androidx.annotation.DrawableRes
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.models.shop.IShopItem
import java.util.UUID

abstract class BackgroundItem(
    override val id: UUID,
    override val name: Int,
    override val description: String,
    override val price: Int,
    @DrawableRes override val assetId: Int
) : IShopItem {
    object Forest: BackgroundItem(
        id = IShopItem.BACKGROUND_FOREST_UUID,
        name = R.string.shop_background_forest,
        description = "",
        price = 0,
        assetId = R.drawable.background_forest
    )

    object Mountain: BackgroundItem(
        id = IShopItem.BACKGROUND_MOUNTAIN_UUID,
        name = R.string.shop_background_mountain,
        description = "",
        price = 200,
        assetId = R.drawable.background_mountain
    )

    object Ocean: BackgroundItem(
        id = IShopItem.BACKGROUND_OCEAN_UUID,
        name = R.string.shop_background_ocean,
        description = "",
        price = 200,
        assetId = R.drawable.background_ocean
    )

    object City: BackgroundItem(
        id = IShopItem.BACKGROUND_CITY_UUID,
        name = R.string.shop_background_city,
        description = "",
        price = 200,
        assetId = R.drawable.background_city
    )

    object Space: BackgroundItem(
        id = IShopItem.BACKGROUND_SPACE_UUID,
        name = R.string.shop_background_space,
        description = "",
        price = 500,
        assetId = R.drawable.background_space
    )

    companion object {
        val allBackgrounds = listOf(Forest, Mountain, Ocean, City, Space)
        fun fromId(id: UUID): BackgroundItem? = allBackgrounds.firstOrNull { it.id == id }
    }
}
