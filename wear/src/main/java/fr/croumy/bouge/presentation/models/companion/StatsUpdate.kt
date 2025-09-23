package fr.croumy.bouge.presentation.models.companion

abstract class StatsUpdate {
    abstract val value: Float

    class UP(by: Float): StatsUpdate() {
        override val value: Float = by
    }

    class DOWN(by: Float): StatsUpdate() {
        override val value: Float = by
    }
}