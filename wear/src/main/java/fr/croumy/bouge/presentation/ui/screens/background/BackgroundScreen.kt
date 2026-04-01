package fr.croumy.bouge.presentation.ui.screens.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.fillMaxRectangle
import com.google.android.horologist.compose.rotaryinput.rotaryWithScroll
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.models.app.ShopItemType
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.ShopItemComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun BackgroundScreen(
    viewModel: BackgroundViewModel = hiltViewModel()
) {
    val lazyGridState = rememberLazyGridState()

    Box {
        Image(
            painterResource(R.drawable.background_sky_day),
            contentDescription = stringResource(R.string.description_cloudy_background),
        )

        LazyVerticalGrid(
            state = lazyGridState,
            modifier = Modifier
                .fillMaxRectangle()
                .rotaryWithScroll(scrollableState = lazyGridState),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding)
        ) {
            items(viewModel.allBackgroundItems.value) { item ->
                val isSelected = viewModel.companion.value?.backgroundId == item.id

                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable{ viewModel.selectBackground(item.id) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.inventory_box),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(item.assetId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Dimensions.smallPadding + Dimensions.xsmallPadding)
                            .offset(y = -Dimensions.smallPadding)
                    )

                    if(isSelected) {
                        Image(
                            painterResource(R.drawable.icon_selected),
                            contentDescription = stringResource(R.string.description_selected_wallpaper),
                            modifier = Modifier
                                .size(Dimensions.xsmallIcon)
                                .align(Alignment.TopEnd)
                        )
                    }
                }
            }
        }
    }
}