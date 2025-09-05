package fr.croumy.bouge.presentation.models.companion

abstract class StatsType {
    abstract val value: Int

    class UP(by: Int): StatsType() {
        override val value: Int = by
    }

    class DOWN(by: Int): StatsType() {
        override val value: Int = by
    }
}