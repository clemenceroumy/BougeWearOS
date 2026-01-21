package fr.croumy.bouge.presentation.ui.screens.historyCompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.extensions.asString
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.core.ui.components.AnimatedSprite

@Composable
fun HistoryCompanionScreen(
    viewModel: HistoryCompanionViewModel = hiltViewModel()
) {
    ScalingLazyColumn {
        items(viewModel.companions.value) { companion ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(Dimensions.mediumRadius))
                    .padding(Dimensions.smallPadding)
            ) {
                AnimatedSprite(
                    modifier = Modifier.size(Dimensions.largeIcon),
                    imageId = companion.type.assetIdleId,
                    frameCount = companion.type.assetIdleFrame,
                )
                Column {
                    Text(companion.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(Dimensions.xsmallPadding))
                    Text(stringResource(R.string.companion_age, companion.age), style = MaterialTheme.typography.labelMedium)
                    Text(companion.birthDate.asString(), style = MaterialTheme.typography.labelMedium)
                    Text(companion.deathDate!!.asString(), style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}