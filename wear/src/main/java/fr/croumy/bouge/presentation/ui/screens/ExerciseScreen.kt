package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val isWalking = viewModel.isWalking.collectAsState()
    val totalSteps = viewModel.totalSteps.collectAsState()
    val exerciseEvents = viewModel.tempExerciseEvents.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.DirectionsWalk,
                contentDescription = "Walking Icon",
                Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = totalSteps.value.toString(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        exerciseEvents.value.map {
            Text(
                text = it.toString(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        if (isWalking.value) {
            AnimatedSprite(
                imageId = R.drawable.toxicfroggreenblue_walk,
                frameCount = 7
            )
        } else {
            AnimatedSprite(
                imageId = R.drawable.toxicfroggreenblue_idle,
                frameCount = 8
            )
        }
    }
}