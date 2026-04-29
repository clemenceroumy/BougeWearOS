package fr.croumy.bouge.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.wood_sign
import fr.croumy.bouge.core.theme.Dimensions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WoodPanelComponent(
    modifier: Modifier = Modifier,
    size: Dp = Dimensions.largeIcon,
    text: String,
    paddingTop: Dp = Dimensions.smallPadding,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Box(
        modifier = modifier
            .padding(horizontal = Dimensions.smallPadding)
            .size(size),
        contentAlignment = Alignment.TopCenter,
    ) {
        Image(
            painter = painterResource(Res.drawable.wood_sign),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text,
            modifier = Modifier.padding(top = paddingTop),
            style = style,
            textAlign = TextAlign.Center
        )
    }
}