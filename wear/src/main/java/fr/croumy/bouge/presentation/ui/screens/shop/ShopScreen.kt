package fr.croumy.bouge.presentation.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.IShopItem
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.theme.Dimensions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ShopScreen(
    shopViewModel: ShopViewModel = hiltViewModel()
) {
    val totalCredit = shopViewModel.totalCredit.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = shopViewModel.snackHostState) },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(totalCredit.value.toString())
            LazyVerticalGrid(
                modifier = Modifier.fillMaxRectangle(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Column {
                        Text(stringResource(R.string.shop_type_food))
                        Spacer(Modifier.size(Dimensions.xsmallPadding))
                    }
                }
                items(FoodItem.allFood) { item ->
                    ShopItemComponent(item, onClick = { shopViewModel.buyItem(item.price, item.id) })
                }

                item(span = { GridItemSpan(2) }) {
                    Column {
                        Text(stringResource(R.string.shop_type_background))
                        Spacer(Modifier.size(Dimensions.xsmallPadding))
                    }
                }
                items(BackgroundItem.allBackgrounds) { item ->
                    ShopItemComponent(
                        item,
                        disabled = shopViewModel.getAlreadyPossessedBackgrounds.value.any { it.id == item.id },
                        { shopViewModel.buyItem(item.price, item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ShopItemComponent(
    item: IShopItem,
    disabled: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .clickable { if(!disabled) onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(Dimensions.mediumRadius)
                )
                .padding(Dimensions.xsmallPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(item.name),
                style = MaterialTheme.typography.bodySmall
            )
            Image(
                painter = painterResource(item.assetId),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimensions.iconBtnHeight)
                    .aspectRatio(1f),
                colorFilter = if(disabled) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null
            )
            Spacer(Modifier.size(0.dp))
        }

        if(!disabled) Box(
            Modifier
                .align(Alignment.BottomEnd)
                .size(Dimensions.smallIcon)
                .offset(-Dimensions.xsmallPadding, -Dimensions.xsmallPadding)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                item.price.toString(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}