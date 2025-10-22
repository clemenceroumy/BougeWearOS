package fr.croumy.bouge.presentation.extensions

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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

fun Instant.toYYYYMMDD(): String {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ZonedDateTime.ofInstant(this, ZoneId.systemDefault()))
}

fun ZonedDateTime.asString(): String {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(this)
}