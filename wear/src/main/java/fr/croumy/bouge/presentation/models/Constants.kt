package fr.croumy.bouge.presentation.models

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object Constants {
    val TIME_STOPPED_WALKING = 2.seconds
    const val MINIMUM_STEPS_WALK = 500 // Minimum steps to consider a walk valid
    val TIME_GAP_BETWEEN_WALKS = 2.minutes // A walk is considered finished after this time
}