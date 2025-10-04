package fr.croumy.bouge.presentation.models.companion

import fr.croumy.bouge.R

enum class StatsType {
    HAPPINESS {
        override var assets: List<Pair<Float, Int>> = listOf(
            0f to R.drawable.happiness_0,
            0.25f to R.drawable.happiness_0_25,
            0.5f to R.drawable.happiness_0_5,
            0.75f to R.drawable.happiness_0_75,
            1.0f to R.drawable.happiness_1
        )
    },
    HUNGRINESS {
        override var assets: List<Pair<Float, Int>> = listOf(
            0f to R.drawable.hungriness_0,
            0.25f to R.drawable.hungriness_0_25,
            0.5f to R.drawable.hungriness_0_5,
            0.75f to R.drawable.hungriness_0_75,
            1.0f to R.drawable.hungriness_1
        )
    },
    HEALTH {
        override var assets: List<Pair<Float, Int>> = listOf(
            0f to R.drawable.health_0,
            0.25f to R.drawable.health_0_25,
            0.5f to R.drawable.health_0_5,
            0.75f to R.drawable.health_0_75,
            1.0f to R.drawable.health_1
        )
    };

    open var assets: List<Pair<Float, Int>> = emptyList()
    fun assetFromProgress(progress: Float): Int {
        return this.assets.find { it.first == progress }!!.second
    }

}