package fr.croumy.bouge.core.models.companion

import fr.croumy.bouge.core.mr.SharedRes

abstract class CompanionType(
    val assetIdleId: Int,
    val assetIdleFrame: Int,
    val assetWalkingId: Int,
    val assetWalkingFrame: Int,
    val defaultName: String
) {
    object Frog : CompanionType(SharedRes.images.idle_frog.drawableResId, 4, SharedRes.images.walking_frog.drawableResId, 7, "Froggy")
    object Pig : CompanionType(SharedRes.images.idle_pig.drawableResId, 5, SharedRes.images.walking_pig.drawableResId, 4, "Piggy")
    object Fox : CompanionType(SharedRes.images.idle_fox.drawableResId, 2, SharedRes.images.walking_fox.drawableResId, 4, "Foxy")
    object Duck : CompanionType(SharedRes.images.idle_duck.drawableResId, 2, SharedRes.images.walking_duck.drawableResId, 6, "Ducky")

    companion object {
        val values = listOf(Frog, Pig, Fox, Duck)
        fun fromString(name: String): CompanionType {
            return values.find { it.javaClass.simpleName == name }!!
        }
    }
}