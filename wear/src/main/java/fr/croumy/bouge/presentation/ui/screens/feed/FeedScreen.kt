package fr.croumy.bouge.presentation.ui.screens.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_sky_day
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.models.app.ShopItemType
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import fr.croumy.bouge.presentation.ui.components.ShopItemComponent
import fr.croumy.bouge.presentation.ui.screens.feed.components.FeedCompanionAnimation
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun FeedScreen(
    feedViewModel: FeedViewModel = hiltViewModel()
) {
    val selectedFoodItem = remember { mutableStateOf<FoodItem?>(null) }
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(Res.drawable.background_sky_day),
            contentDescription = stringResource(R.string.description_cloudy_background),
        )

        if(feedViewModel.isLoading.value) {
            Column(
                modifier = Modifier.fillMaxRectangle(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedSprite(
                    modifier = Modifier.size(Dimensions.smallIcon),
                    image = ImageBitmap.imageResource(R.drawable.loader),
                    frameCount = 16
                )
            }
        } else {
            if (feedViewModel.allFoodItems.value.isEmpty())
                Column(
                    modifier = Modifier
                        .fillMaxRectangle(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.icon_inventory),
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.smallIcon)
                    )
                    Spacer(Modifier.height(Dimensions.smallPadding))
                    OutlinedText(
                        text = stringResource(R.string.inventory_empty),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            else LazyVerticalGrid(
                state = lazyGridState,
                modifier = Modifier
                    .fillMaxRectangle()
                    .onRotaryScrollEvent { event ->
                        coroutineScope.launch {
                            lazyGridState.scrollBy(event.verticalScrollPixels)
                        }
                        true
                    }
                    .focusRequester(focusRequester)
                    .focusable(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
                contentPadding = PaddingValues(bottom = Dimensions.largePadding)
            ) {
                items(feedViewModel.allFoodItems.value) { item ->
                    ShopItemComponent(
                        ShopItemType.INVENTORY,
                        item.first,
                        text = "x${item.second}",
                        onClick = { selectedFoodItem.value = item.first }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = selectedFoodItem.value != null,
            modifier = Modifier.fillMaxSize()
        ) {
            if (feedViewModel.companion.value != null && selectedFoodItem.value != null) {
                FeedCompanionAnimation(
                    companion = feedViewModel.companion.value!!,
                    foodItem = selectedFoodItem.value!!,
                    onEnd = {
                        feedViewModel.feedCompanion(selectedFoodItem.value!!)
                        selectedFoodItem.value = null
                    }
                )
            }
        }
    }
}