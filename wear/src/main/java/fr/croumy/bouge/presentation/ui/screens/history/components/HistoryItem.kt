package fr.croumy.bouge.presentation.ui.screens.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.croumy.bouge.presentation.models.Walk
import fr.croumy.bouge.presentation.theme.Dimensions
import kotlin.time.DurationUnit
import kotlin.time.toKotlinDuration

@Composable
fun HistoryItem(
    walk: Walk
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(Dimensions.mediumRadius))
            .padding(Dimensions.smallPadding)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${walk.steps} steps")
            Text("${walk.duration.toKotlinDuration()}")
        }

        Text("${walk.startTime} - ${walk.endTime}")
    }
}