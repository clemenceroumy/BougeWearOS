package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.InventoryEntity

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory")
    fun getInventory(): List<InventoryEntity>

    @Insert
    fun insertItem(item: InventoryEntity)
}