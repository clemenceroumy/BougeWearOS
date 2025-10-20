package fr.croumy.bouge.presentation.ui.screens.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Text

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val accelerometer = viewModel.accelerometer.collectAsState()
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(accelerometer.value.toString())
    }
}