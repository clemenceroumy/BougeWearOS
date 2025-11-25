package fr.croumy.bouge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.ui.components.AnimatedSprite

@Composable
fun MainScreen(
    companion: Companion
) {
    Column {
        Text(companion.name)
        Text(companion.age.toString())
        AnimatedSprite(
            modifier = Modifier.size(120.dp),
            imageId = companion.type.assetIdleId,
            frameCount = companion.type.assetIdleFrame,
        )
    }
}