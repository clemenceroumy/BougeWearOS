package fr.croumy.bouge.presentation.ui.screens.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.croumy.bouge.presentation.models.Walk

@Composable
fun HistoryItem(
    walk: Walk
) {
    Column {
        Text("steps: ${walk.steps}")
        Text(walk.startTime.toString())
        Text(walk.endTime.toString())
    }
}