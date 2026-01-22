package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.presentation.data.entities.InventoryEntity
import java.util.UUID

@Dao
interface InventoryDao {
    companion object {
        val INSERT_DEFAULT_BACKGROUND = "INSERT INTO inventory (uid, itemId) VALUES ('${BackgroundItem.MountainTree.id}','${BackgroundItem.MountainTree.id}')"
    }

    @Query("SELECT * FROM inventory")
    suspend fun getAll(): List<InventoryEntity>

    @Insert
    suspend fun insertItem(item: InventoryEntity)

    @Query("DELETE FROM inventory WHERE rowid = (SELECT rowid FROM inventory WHERE itemId = :itemId LIMIT 1)")
    suspend fun deleteItem(itemId: UUID)
}