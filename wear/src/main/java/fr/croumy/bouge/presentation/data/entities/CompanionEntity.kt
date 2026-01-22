package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import java.time.ZonedDateTime
import java.util.UUID

@Entity(tableName = "companions")
data class CompanionEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    val name: String,
    val type: String,
    val birthDate: ZonedDateTime,
    val deathDate: ZonedDateTime? = null,
    val currentBackgroundUid: UUID = BackgroundItem.Mountain.id,
    val totalSteps: Long = 0,
    val happiness: Float = 5f, // 0 TO 5, can be .25
    val hungriness: Float = 5f, // 0 TO 5, can be .25
    val health: Float = 5f, // 0 TO 5, can be .5,
)