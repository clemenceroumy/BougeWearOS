package fr.croumy.bouge.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.core.theme.Dimensions

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    size: Dp = Dimensions.iconBtnHeight,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(size - 10.dp)
        )
    }
}