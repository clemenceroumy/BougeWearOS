package fr.croumy.bouge.presentation.ui.screens.shop

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.CreditService
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    val creditService: CreditService
): ViewModel() {
    val totalCredit = creditService.getTotalCredits()

    fun buyItem(amount: Int, itemId: Int?) {
        creditService.spendCredit(amount, itemId)
        // TODO: add item to inventory
    }
}