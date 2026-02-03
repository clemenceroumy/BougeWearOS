package fr.croumy.bouge.presentation.ui.screens.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.constants.Constants
import fr.croumy.bouge.core.models.companion.StatsType
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CompanionStatsScreen(
    viewModel: CompanionStatsViewModel = hiltViewModel()
) {
    val companion = viewModel.companion.collectAsState()
    val stats = viewModel.stats.collectAsState()

    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(R.drawable.background_sky_day),
            contentDescription = stringResource(R.string.description_cloudy_background),
        )

        Column(
            Modifier.fillMaxRectangle(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (stats.value == null || companion.value == null) CircularProgressIndicator()
            else Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    text = companion.value!!.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedText(
                    text = stringResource(R.string.companion_age, companion.value!!.age)
                )
                Spacer(modifier = Modifier.height(Dimensions.smallPadding))

                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimensions.mediumPadding)
                ) {
                    IconProgressBar(
                        progress = stats.value!!.happiness,
                        stat = StatsType.HAPPINESS
                    )
                    IconProgressBar(
                        progress = stats.value!!.hungriness,
                        stat = StatsType.HUNGRINESS
                    )
                    IconProgressBar(
                        progress = stats.value!!.health,
                        stat = StatsType.HEALTH
                    )
                }
            }
        }
    }
}

@Composable
fun IconProgressBar(
    progress: Float,
    stat: StatsType
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(Dimensions.xsmallIcon)
    ) {
        val full = progress.toInt()
        val partial = progress - full
        val empty = (Constants.STAT_MAX - progress).toInt()

        List(full) { stat.assetFromProgress(1f) }.map {
            IconProgress(it, stat.name)
        }

        if (partial > 0f) {
            val asset = stat.assetFromProgress(partial)
            IconProgress(asset, stat.name)
        }

        List(empty) { stat.assetFromProgress(0f) }.map {
           IconProgress(it, stat.name)
        }
    }
}

@Composable
fun RowScope.IconProgress(
    asset: DrawableResource,
    description: String
) {
    Image(
        painterResource(asset),
        contentDescription = description,
        Modifier
            .weight(1f)
            .aspectRatio(1f)
    )
}