package fr.croumy.bouge.presentation.usecases.shop

import fr.croumy.bouge.presentation.constants.AppError
import fr.croumy.bouge.presentation.services.CreditService
import fr.croumy.bouge.presentation.services.InventoryService
import fr.croumy.bouge.presentation.usecases.IUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class BuyItemParams(
    val itemId: Int,
    val price: Int
)

class BuyItemUseCase @Inject constructor(
    val creditService: CreditService,
    val inventoryService: InventoryService
) : IUseCase<BuyItemParams, Unit> {

    override fun invoke(params: BuyItemParams?) {
        if (params == null) return

        CoroutineScope(Dispatchers.IO).launch {
            if(creditService.getTotalCredits().first() < params.price) {
                throw AppError.NotEnoughCredits
            }

            val transactionId = creditService.spendCredit(params.price, params.itemId)
            inventoryService.addItemToInventory(params.itemId,transactionId)
        }
    }
}