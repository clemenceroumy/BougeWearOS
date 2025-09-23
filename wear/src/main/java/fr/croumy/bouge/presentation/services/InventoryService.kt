package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.data.entities.InventoryEntity
import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.repositories.InventoryRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryService @Inject constructor(
    val inventoryRepository: InventoryRepository
) {
    fun addItemToInventory(itemId: Int, creditEntityId: UUID) {
        inventoryRepository.insertItem(
            InventoryEntity(
                itemId = itemId,
                creditEntityId = creditEntityId
            )
        )
    }

    fun useItem(itemId: Int) {
        inventoryRepository.removeItem(itemId)
    }

    fun getAllFoodItems(): List<Pair<FoodItem, Int>> {
        return inventoryRepository.getAllItems()
            .mapNotNull { inventoryItem -> FoodItem.fromId(inventoryItem.itemId) } // Retrieve only food items
            .groupingBy { it }
            .eachCount()
            .toList()
    }
}