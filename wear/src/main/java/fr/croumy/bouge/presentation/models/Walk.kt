package fr.croumy.bouge.presentation.models

import java.time.Duration
import java.time.ZonedDateTime

data class Walk(
    val steps: Int,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime
) {
    val duration get() = Duration.between(startTime, endTime)
}
