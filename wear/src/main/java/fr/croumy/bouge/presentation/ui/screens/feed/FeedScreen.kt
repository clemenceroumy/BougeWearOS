package fr.croumy.bouge.presentation.ui.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.presentation.theme.Dimensions

@Composable
fun FeedScreen(
    feedViewModel: FeedViewModel = hiltViewModel()
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxRectangle(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding)
    ) {
        items(feedViewModel.allFoodItems) { item ->
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
                    "${item.second} - ${stringResource(item.first.name)}",
                    style = MaterialTheme.typography.bodySmall
                )
                Image(
                    painter = painterResource(item.first.assetId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimensions.iconBtnHeight)
                        .aspectRatio(1f)
                )
            }
        }
    }
}