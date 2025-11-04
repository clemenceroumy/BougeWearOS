package fr.croumy.bouge.presentation.usecases.companion

import fr.croumy.bouge.presentation.background.workers.WorkerHelper
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.models.shop.toy.ToyItem
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.usecases.IUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayParams(
    val playItem: ToyItem
)

class PlayUseCase @Inject constructor(
    val companionService: CompanionService,
    val workerHelper: WorkerHelper
): IUseCase<PlayParams, Unit> {
    override fun invoke(params: PlayParams?) {
        if (params == null) return

        CoroutineScope(Dispatchers.IO).launch {
            companionService.updateHappinessStat(StatsUpdate.UP(params.playItem.boost))

            // TRIGGER HAPPINESS WORKER TO DECREASE HAPPINESS AGAIN IN 24 HOURS
            workerHelper.launchHappinessWorker()
        }
    }
}