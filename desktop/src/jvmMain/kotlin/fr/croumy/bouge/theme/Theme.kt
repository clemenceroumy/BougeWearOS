package fr.croumy.bouge.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

val lightTheme = darkColorScheme(
    primary = Color(0xFF3AB795)
)

@Composable
fun BougeTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalContentColor provides Color.White) { // DEFAULT COLOR FOR TEXT
        MaterialTheme(
            colorScheme = lightTheme,
            typography = AppTypography(),
            content = content
        )
    }
}