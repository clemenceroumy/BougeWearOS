package fr.croumy.bouge.presentation.extensions

import java.time.Duration
import java.time.LocalTime
import java.time.ZonedDateTime
import kotlin.time.toKotlinDuration


fun Duration.asString(): String {
    return this.toKotlinDuration().toComponents { days, hours, minutes, seconds, nanoseconds ->
        val hour = if(hours > 0) "${hours}h" else ""
        val minute = if(hours > 0 || minutes > 0) "${minutes}m" else ""
        val second = if (hours == 0) "${seconds}s" else ""

        "${hour}${minute}${second}"
    }
}

fun LocalTime.asString(): String {
    return "${this.hour}:${this.minute}"
}