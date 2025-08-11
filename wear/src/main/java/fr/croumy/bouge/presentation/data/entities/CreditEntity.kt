package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.croumy.bouge.presentation.models.ExerciseType
import java.time.ZonedDateTime

@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val value: Int,
    val type: ExerciseType,
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)