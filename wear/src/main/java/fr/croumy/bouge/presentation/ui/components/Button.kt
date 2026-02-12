package fr.croumy.bouge.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.theme.Dimensions

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String? = null,
    @DrawableRes icon: Int? = null,
    size: Dp = Dimensions.btnHeight,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .clickable(enabled) { onClick() }
            .height(size)
            .wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        NinePatchImage(
            R.drawable.btn,
            modifier = Modifier.matchParentSize(),
        )

        Row(
            modifier = Modifier
                .padding(horizontal = Dimensions.mediumPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 12.dp)
                        .aspectRatio(1f)
                )
            }

            if(icon != null && label != null) {
                Spacer(Modifier.width(Dimensions.xsmallPadding))
            }

            label?.let { Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground
            ) }
        }
    }
}

