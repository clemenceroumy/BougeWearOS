package fr.croumy.bouge.presentation.ui.screens.shop

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.constants.AppError
import fr.croumy.bouge.presentation.services.CreditService
import fr.croumy.bouge.presentation.usecases.shop.BuyItemParams
import fr.croumy.bouge.presentation.usecases.shop.BuyItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    val creditService: CreditService,
    val buyItemUseCase: BuyItemUseCase,
    val context: Context
): ViewModel() {
    val snackHostState = SnackbarHostState()
    val totalCredit = creditService.getTotalCredits().stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0
    )

    fun buyItem(amount: Int, itemId: Int) {
        try {
            buyItemUseCase(
                BuyItemParams(
                    itemId = itemId,
                    price = amount
                )
            )
        } catch (e: Exception) {
            if(e is AppError) {
                viewModelScope.launch {
                    snackHostState.showSnackbar(
                        context.getString(e.stringRes)
                    )
                }
            }
        }

    }
}