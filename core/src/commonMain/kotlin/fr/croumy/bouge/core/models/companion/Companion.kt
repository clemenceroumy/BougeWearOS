package fr.croumy.bouge.core.models.companion

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.Duration
import java.time.ZonedDateTime
import java.util.UUID

@Serializable
data class Companion(
    val name: String,
    val type: CompanionType,
    val birthDate: ZonedDateTime,
    val deathDate: ZonedDateTime?,
    val backgroundId: UUID?,
) {
    @Suppress("NewApi")
    val age: Int = Duration.between(birthDate, deathDate ?: ZonedDateTime.now()).toDays().toInt()

    fun encodeToJson(): String {
        return Json.encodeToString(this)
    }

    companion object {
        fun decodeFromJson(json: String): fr.croumy.bouge.core.models.companion.Companion {
            return Json.decodeFromString(json)
        }
    }
}