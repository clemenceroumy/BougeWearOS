package fr.croumy.bouge.core.models.companion

import fr.croumy.bouge.core.utils.serializer.KUUIDSerializer
import fr.croumy.bouge.core.utils.serializer.KZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.Duration
import java.time.ZonedDateTime
import java.util.UUID

@Serializable
data class Companion(
    val name: String,
    val type: CompanionType,
    @Serializable(KZonedDateTimeSerializer::class) val birthDate: ZonedDateTime,
    @Serializable(KZonedDateTimeSerializer::class) val deathDate: ZonedDateTime?,
    @Serializable(KUUIDSerializer::class) val backgroundId: UUID?,
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