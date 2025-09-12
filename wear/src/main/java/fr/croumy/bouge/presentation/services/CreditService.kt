package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.data.entities.CreditEntity
import fr.croumy.bouge.presentation.models.exercise.ExerciseType
import fr.croumy.bouge.presentation.repositories.CreditRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditService @Inject constructor(
    val creditRepository: CreditRepository
) {
    fun getTotalCredits() = creditRepository.getTotalCredits().stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0
    )

    fun insertCredit(amount: Int, type: ExerciseType? = null, exerciseId: UUID? = null) {
        creditRepository.insertCredit(CreditEntity(value = amount, type = type, exerciseUid = exerciseId))
    }

    fun spendCredit(amount: Int, shopId: Int?) {
        creditRepository.insertCredit(CreditEntity(value = -amount, shopUid = shopId))
    }
}