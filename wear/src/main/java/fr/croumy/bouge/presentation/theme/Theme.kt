package fr.croumy.bouge.presentation.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val lightTheme = darkColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface
)

@Composable
fun BougeTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalContentColor provides primary) { // DEFAULT COLOR FOR TEXT
        MaterialTheme(
            colorScheme = lightTheme,
            typography = AppTypography(),
            content = content
        )
    }
}