package fr.croumy.bouge.presentation.models.companion

abstract class StatsUpdate {
    abstract val value: Int

    class UP(by: Int): StatsUpdate() {
        override val value: Int = by
    }

    class DOWN(by: Int): StatsUpdate() {
        override val value: Int = by
    }
}