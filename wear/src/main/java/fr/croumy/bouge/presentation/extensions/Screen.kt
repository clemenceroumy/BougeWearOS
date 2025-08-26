package fr.croumy.bouge.presentation.extensions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import kotlin.math.sqrt


fun Modifier.fillMaxRectangleWidth(): Modifier = composed(
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
    val maxSquareEdge = (sqrt(((screenHeightDp * screenWidthDp) / 2).toDouble()))
    val inset = Dp(((screenHeightDp - maxSquareEdge) / 2).toFloat())

    fillMaxWidth().padding(horizontal = inset)
}