package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.data.entities.InventoryEntity
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.repositories.InventoryRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryService @Inject constructor(
    val inventoryRepository: InventoryRepository
) {
    suspend fun addItemToInventory(itemId: UUID, creditEntityId: UUID? = null) {
        inventoryRepository.insertItem(
            InventoryEntity(
                itemId = itemId,
                creditEntityId = creditEntityId
            )
        )
    }

    suspend fun useItem(itemId: UUID) {
        inventoryRepository.removeItem(itemId)
    }

    suspend fun getAllFoodItems(): List<Pair<FoodItem, Int>> {
        return inventoryRepository.getAllItems()
            .mapNotNull { inventoryItem -> FoodItem.fromId(inventoryItem.itemId) } // Retrieve only food items
            .sortedBy { it.id }
            .groupingBy { it }
            .eachCount()
            .toList()
    }

    suspend fun getAllBackgroundItems(): List<BackgroundItem> {
        return inventoryRepository.getAllItems()
            .mapNotNull { inventoryItem -> BackgroundItem.fromId(inventoryItem.itemId) }
            .toList()
    }
}