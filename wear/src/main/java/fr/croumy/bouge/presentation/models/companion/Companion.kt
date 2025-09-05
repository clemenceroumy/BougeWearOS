package fr.croumy.bouge.presentation.models.companion

import java.time.Duration
import java.time.ZonedDateTime

data class Companion(
    val name: String,
    val type: CompanionType,
    val birthDate: ZonedDateTime,
) {
    val age: Int = Duration.between(birthDate, ZonedDateTime.now()).toDays().toInt()
}