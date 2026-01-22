package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.InventoryEntity
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepository @Inject constructor(
    private val database: AppDatabase
) {
    private val inventoryDao = database.inventoryDao()

    suspend fun insertItem(item: InventoryEntity) {
        inventoryDao.insertItem(item)
    }

    suspend fun removeItem(itemId: UUID) {
        inventoryDao.deleteItem(itemId)
    }

    suspend fun getAllItems(): List<InventoryEntity> {
        return inventoryDao.getAll()
    }
}