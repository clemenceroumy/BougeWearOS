package fr.croumy.bouge.core.models.companion

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
    @Suppress("NewApi")
    val age: Int = Duration.between(birthDate, deathDate ?: ZonedDateTime.now()).toDays().toInt()

    override fun toString(): String {
        return "Companion(name='$name', type=$type, birthDate=$birthDate, deathDate=$deathDate, backgroundId=$backgroundId, age=$age)"
    }
}