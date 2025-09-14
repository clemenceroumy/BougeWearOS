package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.InventoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepository @Inject constructor(
    private val database: AppDatabase
) {
    private val inventoryDao = database.inventoryDao()

    fun insertItem(item: InventoryEntity) {
        inventoryDao.insertItem(item)
    }
}