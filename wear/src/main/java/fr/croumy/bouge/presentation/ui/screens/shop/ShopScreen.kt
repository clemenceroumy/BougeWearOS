package fr.croumy.bouge.presentation.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.Button

@Composable
fun ShopScreen(
    shopViewModel: ShopViewModel = hiltViewModel()
) {
    val totalCredit = shopViewModel.totalCredit.collectAsState()

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(totalCredit.value.toString())
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(FoodItem.allFood) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(Dimensions.mediumRadius))
                ) {
                    Text(item.name.toString())
                    Image(
                        painter = painterResource(item.assetId),
                        contentDescription = null
                    )
                    Text(item.price.toString())
                    Button(
                        label = "buy",
                        onClick = { /* TODO: Handle purchase logic */ }
                    )
                }
            }
        }
    }
}