package fr.croumy.bouge.presentation.models.shop.toy

import androidx.annotation.DrawableRes
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.models.shop.IShopItem
import java.util.UUID

abstract class ToyItem(
    override val id: UUID,
    override val name: Int,
    override val description: String,
    override val price: Int,
    @DrawableRes override val assetId: Int,
    val boost: Float
) : IShopItem {
    object Ball: ToyItem(
        id = IShopItem.PLAY_BALL_UUID,
        name = R.string.shop_play_ball,
        description = "",
        price = 0,
        assetId = R.drawable.ball,
        boost = 0.5f
    )

    companion object {
        val allPlayItems = listOf(Ball)
        fun fromId(id: UUID): ToyItem? = allPlayItems.firstOrNull { it.id == id }
    }
}
