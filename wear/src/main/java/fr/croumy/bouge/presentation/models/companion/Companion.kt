package fr.croumy.bouge.presentation.models.companion

import java.time.Duration
import java.time.ZonedDateTime
import java.util.UUID

data class Companion(
    val name: String,
    val type: CompanionType,
    val birthDate: ZonedDateTime,
    val deathDate: ZonedDateTime?,
    val backgroundId: UUID?,
) {
    val age: Int = Duration.between(birthDate, deathDate ?: ZonedDateTime.now()).toDays().toInt()
}