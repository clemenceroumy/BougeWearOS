package fr.croumy.bouge.presentation.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.IShopItem
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.extensions.fillMaxRectangleWidth
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import org.jetbrains.compose.resources.painterResource

@Composable
fun ShopScreen(
    shopViewModel: ShopViewModel = hiltViewModel()
) {
    val totalCredit = shopViewModel.totalCredit.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = shopViewModel.snackHostState) },
        contentColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box {
            Image(
                painterResource(R.drawable.background_sky_day),
                contentDescription = stringResource(R.string.description_cloudy_background),
            )

            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.padding(top = Dimensions.mediumPadding)
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon_coin),
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.xxsmallIcon)
                    )
                    OutlinedText(
                        text = totalCredit.value.toString()
                    )
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxRectangleWidth()
                        .padding(top = Dimensions.smallPadding),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
                    contentPadding = PaddingValues(bottom = Dimensions.largePadding)
                ) {
                    item(span = { GridItemSpan(2) }) {
                        Column(Modifier.padding(start = Dimensions.smallPadding)) {
                            OutlinedText(text = stringResource(R.string.shop_type_food))
                            Spacer(Modifier.size(Dimensions.xsmallPadding))
                        }
                    }
                    items(FoodItem.allFood) { item ->
                        ShopItemComponent(item, onClick = { shopViewModel.buyItem(item.price, item.id) })
                    }

                    item(span = { GridItemSpan(2) }) {
                        Column(Modifier.padding(start = Dimensions.smallPadding)) {
                            Spacer(Modifier.size(Dimensions.smallPadding))
                            OutlinedText(text = stringResource(R.string.shop_type_background))
                            Spacer(Modifier.size(Dimensions.xsmallPadding))
                        }
                    }
                    items(BackgroundItem.allBackgrounds) { item ->
                        ShopItemComponent(
                            item,
                            itemSize = Dimensions.mediumIcon,
                            disabled = shopViewModel.getAlreadyPossessedBackgrounds.value.any { it.id == item.id },
                            onClick = { shopViewModel.buyItem(item.price, item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShopItemComponent(
    item: IShopItem,
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
                text = item.price.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = if (disabled) Color.Gray else Color.White
            )
            Image(
                painter = painterResource(R.drawable.icon_coin),
                contentDescription = null,
                modifier = Modifier.size(Dimensions.xxsmallIcon),
                colorFilter = if (disabled) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null
            )
        }
    }
}