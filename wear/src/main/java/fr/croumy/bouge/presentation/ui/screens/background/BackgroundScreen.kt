package fr.croumy.bouge.presentation.ui.screens.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.presentation.theme.Dimensions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BackgroundScreen(
    viewModel: BackgroundViewModel = hiltViewModel()
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxRectangle(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.xsmallPadding)
    ) {
        items(viewModel.allBackgroundItems.value) { item ->
            val isSelected = viewModel.companion.value?.backgroundId == item.id

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(Dimensions.mediumRadius))
                    .border(
                        1.dp,
                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(Dimensions.mediumRadius)
                    )
                    .clickable { viewModel.selectBackground(item.id) }
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
            }
        }
    }
}