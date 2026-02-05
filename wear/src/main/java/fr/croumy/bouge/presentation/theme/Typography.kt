package fr.croumy.bouge.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import fr.croumy.bouge.R

val jerseyFont = FontFamily(
    Font(resId = R.font.jersey15)
)
fun AppTypography() = Typography(
    displayLarge = TextStyle(
        fontSize = 30.sp,
        fontFamily = jerseyFont
    ),
    displayMedium = TextStyle(
        fontSize = 26.sp,
        fontFamily = jerseyFont
    ),
    bodyLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = jerseyFont
    ),
    bodyMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = jerseyFont
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = jerseyFont
    ),
    labelMedium = TextStyle(
        fontSize = 10.sp,
        fontFamily = jerseyFont
    )
)