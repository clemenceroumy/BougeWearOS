package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.croumy.bouge.presentation.models.exercise.ExerciseType
import java.time.ZonedDateTime
import java.util.UUID

@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    val value: Int,
    val type: ExerciseType? = null,
    val timestamp: ZonedDateTime = ZonedDateTime.now(),
    // TODO: link to specific exercise or shop entity
    val exerciseUid: UUID? = null, // credit won from a specific exercise
    val shopUid: Int? = null // credit spent in the shop
)