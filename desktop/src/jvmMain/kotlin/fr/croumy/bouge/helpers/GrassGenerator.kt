package fr.croumy.bouge.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.grass_0
import bouge.desktop.generated.resources.grass_1
import bouge.desktop.generated.resources.grass_2
import bouge.desktop.generated.resources.grass_3
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.core.theme.Dimensions
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random


val grassAssets = listOf(Res.drawable.grass_0, Res.drawable.grass_1, Res.drawable.grass_2, Res.drawable.grass_3)

val grass = List(size = (Window.WIDTH / Dimensions.mediumIcon.value.toInt()) + 2) {
    grassAssets[Random.nextInt(grassAssets.size)]
}

@Composable
fun GrassGenerator(modifier: Modifier = Modifier) {
    Row(
        modifier.wrapContentWidth()
    ) {
        grass.forEach {
            Image(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.size(Dimensions.smallIcon + Dimensions.xxxsmallIcon),
                contentScale = ContentScale.Crop
            )
        }
    }
}