package fr.croumy.bouge.presentation.ui.screens.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import fr.croumy.bouge.presentation.ui.screens.history.components.HistoryItem

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun ExercisesHistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val walksByDay = viewModel.walksByDay

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

    ) {
        walksByDay.forEach { day, walks ->
            item {
                Text(day.toString())
            }

            items(walks) { walk ->
                HistoryItem(walk)
            }
        }
    }
}