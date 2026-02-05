package fr.croumy.bouge.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    size: Dp = Dimensions.btnHeight
) {
    val sizeModifier = if(label != null) modifier.height(size).fillMaxWidth() else modifier.aspectRatio(1f)

    Box(
        sizeModifier
    ) {
        NinePatchImage(
            R.drawable.cloud_btn,
            modifier = Modifier.fillMaxSize(),
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() },
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

            label?.let { Text(text = label) }
        }
    }
}

