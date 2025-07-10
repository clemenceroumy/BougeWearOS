package fr.croumy.bouge.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun BougeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        content = content
    )
}