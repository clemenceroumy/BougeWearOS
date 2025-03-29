package fr.croumy.bouge.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    Text("Exercise")
}