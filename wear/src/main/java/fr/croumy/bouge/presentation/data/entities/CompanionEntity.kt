package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime
import java.util.UUID

@Entity(tableName = "companions")
data class CompanionEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    val name: String,
    val asset: String,
    val birthDate: ZonedDateTime,
    val deathDate: ZonedDateTime? = null,
    val totalSteps: Long,
    val happiness: Int, // 0 TO 5
    val hungriness: Int, // 0 TO 5
    val health: Int, // 0 TO 5
)