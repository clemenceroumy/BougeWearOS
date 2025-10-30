package fr.croumy.bouge.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import fr.croumy.bouge.presentation.theme.Dimensions

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    icon: Int? = null,
    size: Dp = Dimensions.btnHeight
) {
    BtnItem(
        modifier,
        onClick,
        label,
        icon = {
            icon?.let {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                )
            }
        },
        size = size
    )
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    icon: ImageVector?,
    size: Dp = Dimensions.btnHeight
) {
    BtnItem(
        modifier,
        onClick,
        label,
        icon = {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                )
            }
        },
        size = size
    )
}

@Composable
fun BtnItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    icon: @Composable () -> Unit = {},
    size: Dp
) {
    Row(
        modifier = modifier
            .height(size)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)),
                shape = RoundedCornerShape(Dimensions.mediumRadius)
            )
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Text(
            text = label
        )
    }
}

