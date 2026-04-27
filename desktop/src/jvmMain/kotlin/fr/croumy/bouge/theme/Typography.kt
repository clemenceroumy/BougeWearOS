package fr.croumy.bouge.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.jersey15
import org.jetbrains.compose.resources.Font

@Composable
fun AppTypography(): Typography {
    val jerseyFont = FontFamily(
        Font(Res.font.jersey15)
    )

    return Typography(
        bodyMedium = TextStyle(
            fontSize = 18.sp,
            fontFamily = jerseyFont
        ),
        bodySmall = TextStyle(
            fontSize = 12.sp,
            fontFamily = jerseyFont
        ),
    )
}