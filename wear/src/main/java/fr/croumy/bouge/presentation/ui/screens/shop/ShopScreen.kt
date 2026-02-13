package fr.croumy.bouge.presentation.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.extensions.fillMaxRectangleWidth
import fr.croumy.bouge.presentation.models.app.ShopItemType
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import fr.croumy.bouge.presentation.ui.components.ShopItemComponent

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
                        ShopItemComponent(
                            ShopItemType.SHOP,
                            item,
                            onClick = { shopViewModel.buyItem(item.price, item.id) }
                        )
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
                            ShopItemType.SHOP,
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