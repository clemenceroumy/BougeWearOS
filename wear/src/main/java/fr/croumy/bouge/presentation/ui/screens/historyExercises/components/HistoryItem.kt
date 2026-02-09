package fr.croumy.bouge.presentation.ui.screens.historyExercises.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.extensions.asString
import fr.croumy.bouge.presentation.models.exercise.Walk
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.NinePatchImage

@Composable
fun HistoryItem(
    walk: Walk
) {
    Box(
        Modifier.fillMaxWidth()
    ) {
        NinePatchImage(
            R.drawable.cloud_btn,
            modifier = Modifier.matchParentSize(),
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = Dimensions.smallPadding)
                .padding(horizontal = Dimensions.mediumPadding),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.icon_clock),
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.xsmallIcon)
                )
                Spacer(Modifier.width(Dimensions.xsmallPadding))
                Text(
                    walk.startTime.asString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(Dimensions.smallPadding))
                Text(
                    walk.duration.asString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                Modifier.fillMaxWidth(),
            ) {
                Text(
                    "${walk.steps}",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(Modifier.width(Dimensions.xsmallPadding))
                Text(
                    stringResource(R.string.walks_steps),
                    modifier = Modifier.alignByBaseline()
                )
            }
        }
    }
}