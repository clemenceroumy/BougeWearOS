package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import fr.croumy.bouge.presentation.models.ExerciseType

@Entity(tableName = "credits")
data class CreditEntity(
    val value: Int,
    val type: ExerciseType
)