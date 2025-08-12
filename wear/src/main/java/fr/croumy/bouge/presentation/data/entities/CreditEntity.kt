package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.croumy.bouge.presentation.models.ExerciseType
import java.time.ZonedDateTime
import java.util.UUID

@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    val value: Int,
    val type: ExerciseType,
    val timestamp: ZonedDateTime = ZonedDateTime.now(),
    val exerciseUid: UUID
)