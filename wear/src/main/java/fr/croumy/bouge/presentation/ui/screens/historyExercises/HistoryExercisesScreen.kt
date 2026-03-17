package fr.croumy.bouge.presentation.ui.screens.historyExercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import fr.croumy.bouge.presentation.ui.screens.historyExercises.components.HistoryItem

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun ExercisesHistoryScreen(
    viewModel: HistoryExercisesViewModel = hiltViewModel()
) {
    val walksByDay = viewModel.walksByDay

    Box() {
        Image(
            painterResource(R.drawable.background_sky_day),
            contentDescription = stringResource(R.string.description_cloudy_background),
        )

        if(walksByDay.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxRectangle(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(R.drawable.icon_walk),
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.smallIcon)
                )
                Spacer(Modifier.height(Dimensions.smallPadding))
                OutlinedText(
                    text = stringResource(R.string.walks_steps_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                contentPadding = PaddingValues(Dimensions.mediumPadding)
            ) {
                walksByDay.forEach { (day, walks) ->
                    item {
                        OutlinedText(
                            text = day.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    items(walks) { walk ->
                        Column(Modifier.fillMaxWidth()) {
                            HistoryItem(walk)
                            Spacer(Modifier.height(Dimensions.smallPadding))
                        }
                    }

                    item {
                        Spacer(Modifier.height(Dimensions.mediumPadding))
                    }
                }
            }
        }
    }
}