package fr.croumy.bouge.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.IShopItem
import fr.croumy.bouge.presentation.models.app.ShopItemType
import fr.croumy.bouge.presentation.theme.Dimensions
import org.jetbrains.compose.resources.painterResource

@Composable
fun ShopItemComponent(
    shopItemType: ShopItemType,
    item: IShopItem,
    text: String? = null,
    itemSize: Dp = Dimensions.largeIcon,
    disabled: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clickable { if (!disabled) onClick() }
    ) {
        Image(
            painter = painterResource(R.drawable.shop_box),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
        Image(
            painter = painterResource(item.assetId),
            contentDescription = null,
            modifier = Modifier
                .size(itemSize)
                .aspectRatio(1f)
                .align(Alignment.Center)
                .padding(bottom = Dimensions.mediumPadding + Dimensions.xsmallPadding),
            colorFilter = if (disabled) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimensions.xsmallPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when(shopItemType) {
                    ShopItemType.SHOP -> item.price.toString()
                    ShopItemType.INVENTORY -> text!!
                },
                style = MaterialTheme.typography.bodySmall,
                color = if (disabled) Color.Gray else Color.White
            )
            Spacer(Modifier.width(Dimensions.xxsmallPadding))
            Image(
                painter = when (shopItemType) {
                    ShopItemType.SHOP -> painterResource(R.drawable.icon_coin)
                    ShopItemType.INVENTORY -> painterResource(R.drawable.icon_inventory)
                },
                contentDescription = null,
                modifier = Modifier.size(Dimensions.xxsmallIcon),
                colorFilter = if (disabled) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null
            )
        }
    }
}