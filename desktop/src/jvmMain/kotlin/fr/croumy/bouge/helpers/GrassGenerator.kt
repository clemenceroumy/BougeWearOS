package fr.croumy.bouge.helpers

import bouge.desktop.generated.resources.Res
import bouge.desktop.generated.resources.grass_0
import bouge.desktop.generated.resources.grass_1
import bouge.desktop.generated.resources.grass_2
import bouge.desktop.generated.resources.grass_3
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.theme.Dimensions
import kotlin.random.Random


val grassAssets = listOf(Res.drawable.grass_0, Res.drawable.grass_1, Res.drawable.grass_2, Res.drawable.grass_3)

val grass = List(size = (Window.WIDTH / Dimensions.mediumIcon.value.toInt()) + 1) {
    grassAssets[Random.nextInt(grassAssets.size)]
}