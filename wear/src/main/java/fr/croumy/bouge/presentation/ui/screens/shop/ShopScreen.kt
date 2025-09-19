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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.theme.Dimensions

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
                items(FoodItem.allFood) { item ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                shopViewModel.buyItem(item.price, item.id)
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(Dimensions.mediumRadius))
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
                                    .aspectRatio(1f)
                            )
                            Spacer(Modifier.size(0.dp))
                        }

                        Box(
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
            }
        }
    }
}