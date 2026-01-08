package fr.croumy.bouge.services

import androidx.compose.runtime.mutableStateOf
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.shop.food.FoodItem
import kotlinx.serialization.json.Json

class CompanionService {
    val currentCompanion = mutableStateOf<Companion?>(null)
    val currentDrops = mutableStateOf<List<FoodItem>>(emptyList())

    fun resetState() {
        currentCompanion.value = null
        currentDrops.value = emptyList()
    }

    fun currentDropsToByteArray(): ByteArray {
        val dropsIds = currentDrops.value.map { it.id.toString() }
        val dropsJson = Json.encodeToString(dropsIds)

        return dropsJson.toByteArray()
    }
}