package fr.croumy.bouge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.cloud_btn
import fr.croumy.bouge.core.theme.Dimensions
import org.jetbrains.compose.resources.painterResource

@Composable
fun Button(
    text: String,
    icon: @Composable () -> Unit = {},
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .wrapContentWidth()
            .clickable() { onClick() }
    ) {
        Image(
            painterResource(Res.drawable.cloud_btn),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = Dimensions.mediumPadding, vertical = Dimensions.smallPadding)
        ) {
            Text(
                text = text,
                modifier = Modifier,
                style = MaterialTheme.typography.bodySmall
            )
            if(icon != {}) {
                Row {
                    Spacer(Modifier.width(Dimensions.xsmallPadding))
                    icon()
                }
            }
        }
    }
}