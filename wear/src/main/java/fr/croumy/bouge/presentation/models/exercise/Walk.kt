package fr.croumy.bouge.presentation.models.exercise

import java.time.Duration
import java.time.ZonedDateTime

data class Walk(
    val steps: Int,
    val startDateTime: ZonedDateTime,
    val endDateTime: ZonedDateTime
) {
    val duration get() = Duration.between(startDateTime, endDateTime)
    val startTime get() = startDateTime.toLocalTime()
    val endTime get() = endDateTime.toLocalTime()
}
