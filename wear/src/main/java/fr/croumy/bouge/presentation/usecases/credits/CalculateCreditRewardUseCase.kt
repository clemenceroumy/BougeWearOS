package fr.croumy.bouge.presentation.usecases.credits

import fr.croumy.bouge.presentation.models.credit.CreditRewardType
import fr.croumy.bouge.presentation.usecases.IUseCase
import timber.log.Timber
import javax.inject.Inject

data class CalculateCreditRewardParams(
    val value: Int, // steps for walks, repetitions for exercises
    val creditRewardType: CreditRewardType
)

class CalculateCreditRewardUseCase @Inject constructor(): IUseCase<CalculateCreditRewardParams, Int> {
    override fun invoke(params: CalculateCreditRewardParams?): Int {
        if (params == null) {
            Timber.tag("CalculateReward").e("Params cannot be null")
            return 0
        }

        when(params.creditRewardType) {
            CreditRewardType.WALK -> {
                return when(params.value) {
                    in 500..1000 -> 20
                    in 1001..2000 -> 50
                    in 2001..Int.MAX_VALUE -> 100
                    else -> 0
                }
            }
            CreditRewardType.EXERCISE -> {
                return 10
            }
            CreditRewardType.TOTAL_WALK -> {
                return when(params.value) {
                    in 5000..10000 -> 100
                    in 10001..Int.MAX_VALUE -> 200
                    else -> 0
                }
            }
            CreditRewardType.BONUS_EXERCISE -> {
                return 10
            }
        }
    }
}