package fr.croumy.bouge.presentation.ui.screens.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_sky_day
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.core.theme.Dimensions
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun BackgroundScreen(
    viewModel: BackgroundViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box {
        Image(
            painterResource(Res.drawable.background_sky_day),
            contentDescription = stringResource(R.string.description_cloudy_background),
        )

        LazyVerticalGrid(
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