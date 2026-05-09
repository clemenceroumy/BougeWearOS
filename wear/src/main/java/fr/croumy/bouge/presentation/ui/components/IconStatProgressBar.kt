package fr.croumy.bouge.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import fr.croumy.bouge.core.models.companion.StatsType
import fr.croumy.bouge.core.theme.Dimensions
import fr.croumy.bouge.presentation.constants.Constants
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconStatProgressBar(
    progress: Float,
    stat: StatsType,
    displayEmpty: Boolean = true,
) {
    Row(
        Modifier.fillMaxWidth()
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
            if(displayEmpty) IconProgress(it, stat.name)
            else Spacer(Modifier.weight(1f).aspectRatio(1f))
        }
    }
}

@Composable
fun RowScope.IconProgress(
    asset: DrawableResource,
    description: String,
) {
    Image(
        painterResource(asset),
        contentDescription = description,
        Modifier
            .weight(1f)
            .aspectRatio(1f)
    )
}