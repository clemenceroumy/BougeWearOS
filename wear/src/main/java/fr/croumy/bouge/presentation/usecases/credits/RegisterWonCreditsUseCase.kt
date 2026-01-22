package fr.croumy.bouge.presentation.usecases.credits

import fr.croumy.bouge.presentation.models.credit.CreditRewardType
import fr.croumy.bouge.presentation.models.exercise.ExerciseType
import fr.croumy.bouge.presentation.services.CreditService
import fr.croumy.bouge.presentation.usecases.IUseCase
import fr.croumy.bouge.presentation.usecases.IUseCaseSuspend
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

data class RegisterWonCreditsParams(
    val value: Int, // steps for walks, repetitions for exercises
    val creditRewardType: CreditRewardType,
    val exerciseType: ExerciseType? = null, // only for exercises
    val exerciseId: UUID? = null
)

class RegisterWonCreditsUseCase @Inject constructor(
    private val creditService: CreditService
): IUseCaseSuspend<RegisterWonCreditsParams, Unit> {
    override suspend fun invoke(params: RegisterWonCreditsParams?) {
        if (params == null) {
            Timber.tag("CalculateReward").e("Params cannot be null")
            return
        }

        val calculatedCredits = when(params.creditRewardType) {
            CreditRewardType.WALK -> {
                when(params.value) {
                    in 500..1000 -> 20
                    in 1001..2000 -> 50
                    in 2001..Int.MAX_VALUE -> 100
                    else -> 0
                }
            }
            CreditRewardType.EXERCISE -> {
                10
            }
            CreditRewardType.TOTAL_DAILY_STEPS -> {
                when(params.value) {
                    in 5000..10000 -> 100
                    in 10001..Int.MAX_VALUE -> 200
                    else -> 0
                }
            }
            CreditRewardType.BONUS_EXERCISE -> {
                10
            }
        }

        creditService.insertCredit(calculatedCredits, params.exerciseType,params.exerciseId)
    }
}