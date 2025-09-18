package fr.croumy.bouge.presentation.usecases.shop

import fr.croumy.bouge.presentation.constants.AppError
import fr.croumy.bouge.presentation.services.CreditService
import fr.croumy.bouge.presentation.services.InventoryService
import fr.croumy.bouge.presentation.usecases.IUseCase
import fr.croumy.bouge.presentation.usecases.IUseCaseSuspend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BuyItemParams(
    val itemId: Int,
    val price: Int
)

class BuyItemUseCase @Inject constructor(
    val creditService: CreditService,
    val inventoryService: InventoryService
) : IUseCaseSuspend<BuyItemParams, Unit> {

    override suspend fun invoke(params: BuyItemParams?) {
        if (params == null) return

        val totalCredits = creditService.getTotalCredits().first()

        if (totalCredits < params.price) {
            throw AppError.NotEnoughCredits
        }

        val transactionId = creditService.spendCredit(params.price, params.itemId)
        inventoryService.addItemToInventory(params.itemId, transactionId)
    }
}