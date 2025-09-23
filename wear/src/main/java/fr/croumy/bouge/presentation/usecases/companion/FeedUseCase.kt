package fr.croumy.bouge.presentation.usecases.companion

import fr.croumy.bouge.presentation.models.companion.StatsType
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.InventoryService
import fr.croumy.bouge.presentation.usecases.IUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedParams(
    val foodItem: FoodItem
)

class FeedUseCase @Inject constructor(
    val inventoryService: InventoryService,
    val companionService: CompanionService
): IUseCase<FeedParams, Unit> {
    override fun invoke(params: FeedParams?) {
        if (params == null) return

        CoroutineScope(Dispatchers.IO).launch {
            // REMOVE ITEM FROM INVENTORY
            inventoryService.useItem(params.foodItem.id)
            // APPLY STATS BOOST
            params.foodItem.statsBoost.map { entry ->
                when(entry.key) {
                    StatsType.HUNGRINESS -> companionService.updateHungrinessStat(StatsUpdate.UP(entry.value))
                    StatsType.HEALTH -> companionService.updateHealthStat(StatsUpdate.UP(entry.value))
                    StatsType.HAPPINESS -> companionService.updateHappinessStat(StatsUpdate.UP(entry.value))
                }
            }
        }
    }
}