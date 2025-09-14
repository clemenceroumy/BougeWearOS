package fr.croumy.bouge.presentation.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "inventory",
    foreignKeys = [ForeignKey(entity = CreditEntity::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("creditEntityId"),
        onDelete = ForeignKey.SET_NULL)]
)
data class InventoryEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    val itemId: Int,
    val creditEntityId: UUID // TODO: link to specific entity as foreignKey: androidx.room.ForeignKey
)
