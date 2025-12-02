package fr.croumy.bouge.core.models.companion

import bouge.core.generated.resources.Res
import bouge.core.generated.resources.happiness_0
import bouge.core.generated.resources.happiness_0_25
import bouge.core.generated.resources.happiness_0_5
import bouge.core.generated.resources.happiness_0_75
import bouge.core.generated.resources.happiness_1
import bouge.core.generated.resources.hungriness_0
import bouge.core.generated.resources.hungriness_0_25
import bouge.core.generated.resources.hungriness_0_5
import bouge.core.generated.resources.hungriness_0_75
import bouge.core.generated.resources.hungriness_1
import bouge.core.generated.resources.health_0
import bouge.core.generated.resources.health_0_25
import bouge.core.generated.resources.health_0_5
import bouge.core.generated.resources.health_0_75
import bouge.core.generated.resources.health_1
import org.jetbrains.compose.resources.DrawableResource

enum class StatsType {
    HAPPINESS {
        override var assets: List<Pair<Float, DrawableResource>> = listOf(
            0f to Res.drawable.happiness_0,
            0.25f to Res.drawable.happiness_0_25,
            0.5f to Res.drawable.happiness_0_5,
            0.75f to Res.drawable.happiness_0_75,
            1.0f to Res.drawable.happiness_1
        )
    },
    HUNGRINESS {
        override var assets: List<Pair<Float, DrawableResource>> = listOf(
            0f to Res.drawable.hungriness_0,
            0.25f to Res.drawable.hungriness_0_25,
            0.5f to Res.drawable.hungriness_0_5,
            0.75f to Res.drawable.hungriness_0_75,
            1.0f to Res.drawable.hungriness_1
        )
    },
    HEALTH {
        override var assets: List<Pair<Float, DrawableResource>> = listOf(
            0f to Res.drawable.health_0,
            0.25f to Res.drawable.health_0_25,
            0.5f to Res.drawable.health_0_5,
            0.75f to Res.drawable.health_0_75,
            1.0f to Res.drawable.health_1
        )
    };

    open var assets: List<Pair<Float, DrawableResource>> = emptyList()
    fun assetFromProgress(progress: Float): DrawableResource {
        return this.assets.find { it.first == progress }!!.second
    }

}