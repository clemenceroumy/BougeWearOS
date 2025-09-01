package fr.croumy.bouge.presentation.ui.screens.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.theme.Dimensions

@Composable
fun CompanionStatsScreen(
    viewModel: CompanionStatsViewModel = hiltViewModel()
) {
    val companion = viewModel.companion.collectAsState()
    val stats = viewModel.stats.collectAsState()

    Column(
        Modifier.fillMaxRectangle(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (stats.value == null || companion.value == null) CircularProgressIndicator()
        else Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("age: ${companion.value!!.age} days")
            ProgressBar(
                progress = stats.value!!.happiness / Constants.STAT_MAX,
            )
            ProgressBar(
                progress = stats.value!!.hungriness / Constants.STAT_MAX,
            )
            ProgressBar(
                progress = stats.value!!.health / Constants.STAT_MAX,
            )
            Spacer(Modifier.height(0.dp))
        }
    }
}

@Composable
fun ProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val segmentColor = MaterialTheme.colorScheme.onPrimary

    LinearProgressIndicator(
        modifier = modifier
            .height(Dimensions.xsmallIcon)
            .drawWithContent {
                drawContent()

                val segmentSize = this.size.width / Constants.STAT_MAX
                (1 until Constants.STAT_MAX.toInt()).forEach { i ->
                    val x = i * segmentSize
                    drawLine(
                        color = segmentColor,
                        strokeWidth = 2f,
                        start = Offset(x, 0f),
                        end = Offset(x, this.size.height),
                    )
                }
            },
        progress = { progress },
        strokeCap = StrokeCap.Round,
    )
}