package fr.croumy.bouge.presentation.models.companion

import androidx.annotation.DrawableRes
import fr.croumy.bouge.R

abstract class CompanionType(
    @field:DrawableRes val assetIdleId: Int,
    val assetIdleFrame: Int,
    @field:DrawableRes val assetWalkingId: Int,
    val assetWalkingFrame: Int,
    val defaultName: String
) {
    object Frog : CompanionType(R.drawable.idle_frog, 4, R.drawable.walking_frog, 7, "Froggy")
    object Pig : CompanionType(R.drawable.idle_pig, 5, R.drawable.walking_pig, 4, "Piggy")
    object Fox : CompanionType(R.drawable.idle_fox, 2, R.drawable.walking_fox, 4, "Foxy")
    object Duck : CompanionType(R.drawable.idle_duck, 2, R.drawable.walking_duck, 6, "Ducky")

    companion object {
        val values = listOf(Frog, Pig, Fox, Duck)
    }
}