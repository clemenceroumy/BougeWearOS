package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.InventoryEntity
import java.util.UUID

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory")
    suspend fun getAll(): List<InventoryEntity>

    @Insert
    suspend fun insertItem(item: InventoryEntity)

    @Query("DELETE FROM inventory WHERE rowid = (SELECT rowid FROM inventory WHERE itemId = :itemId LIMIT 1)")
    suspend fun deleteItem(itemId: UUID)
}