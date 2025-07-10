package fr.croumy.bouge.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val accelerometerValue = viewModel.accelerometerValue.collectAsState()
    val heartRateValue = viewModel.heartRateValue.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            Text(
                "x: ${accelerometerValue.value.x}",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "y: ${accelerometerValue.value.y}",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "z: ${accelerometerValue.value.z}",
                color = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            "${heartRateValue.value}BPM",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}