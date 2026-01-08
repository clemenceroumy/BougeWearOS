package fr.croumy.bouge.models

enum class Direction {
    RIGHT, LEFT;

    companion object {
        fun random(): Direction {
            return entries.toTypedArray().random()
        }
    }
}