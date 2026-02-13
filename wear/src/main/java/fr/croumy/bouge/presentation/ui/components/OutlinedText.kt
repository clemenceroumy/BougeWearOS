package fr.croumy.bouge.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.theme.onPrimary

@Composable
fun OutlinedText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    outlineColor: Color = onPrimary,
    textAlign: TextAlign = TextAlign.Start
) {
    Box(modifier) {
        Text(
            text = text,
            style = style.copy(
                drawStyle = Stroke(
                    width = Dimensions.outlineTextBorder,
                ),
                color = outlineColor
            ),
            textAlign = textAlign
        )

        Text(
            text = text,
            style = style,
            textAlign = textAlign
        )
    }
}