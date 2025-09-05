package fr.croumy.bouge.presentation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailySteps")
data class DailyStepsEntity(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "total_steps") val totalSteps: Int,
)