package fr.croumy.bouge.core.models.shop.toy

import bouge.core.generated.resources.Res
import bouge.core.generated.resources.ball
import bouge.core.generated.resources.shop_play_ball
import fr.croumy.bouge.core.models.shop.IShopItem
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import java.util.UUID

abstract class ToyItem(
    override val id: UUID,
    override val name: StringResource,
    override val description: String,
    override val price: Int,
    override val assetId: DrawableResource,
    val boost: Float
) : IShopItem {
    object Ball: ToyItem(
        id = IShopItem.PLAY_BALL_UUID,
        name = Res.string.shop_play_ball,
        description = "",
        price = 0,
        assetId = Res.drawable.ball,
        boost = 0.5f
    )

    companion object {
        val allPlayItems = listOf(Ball)
        fun fromId(id: UUID): ToyItem? = allPlayItems.firstOrNull { it.id == id }
    }
}
