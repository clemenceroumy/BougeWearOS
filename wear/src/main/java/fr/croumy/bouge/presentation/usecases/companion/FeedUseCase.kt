package fr.croumy.bouge.presentation.usecases.companion

import fr.croumy.bouge.presentation.background.workers.WorkerHelper
import fr.croumy.bouge.core.models.companion.StatsType
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.InventoryService
import fr.croumy.bouge.presentation.usecases.IUseCase
import fr.croumy.bouge.presentation.usecases.IUseCaseSuspend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedParams(
    val foodItem: FoodItem
)

class FeedUseCase @Inject constructor(
    val inventoryService: InventoryService,
    val companionService: CompanionService,
    val workerHelper: WorkerHelper
): IUseCaseSuspend<FeedParams, Unit> {
    override suspend fun invoke(params: FeedParams?) {
        if (params == null) return

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

        // TRIGGER HUNGRINESS WORKER TO DECREASE HUNGRINESS AGAIN IN 6 HOURS
        workerHelper.launchHungrinessWorker()
    }
}