package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime
import java.util.UUID

@Entity(tableName = "companions")
data class CompanionEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    val name: String,
    val type: String,
    val birthDate: ZonedDateTime,
    val deathDate: ZonedDateTime? = null,
    val totalSteps: Long = 0,
    val happiness: Int = 5, // 0 TO 5
    val hungriness: Int = 5, // 0 TO 5
    val health: Float = 3f, // 0 TO 5, can be .5
)