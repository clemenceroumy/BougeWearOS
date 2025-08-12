package fr.croumy.bouge.presentation.usecases

import fr.croumy.bouge.presentation.models.RewardType
import timber.log.Timber
import javax.inject.Inject

data class CalculateRewardParams(
    val value: Int, // steps for walks, repetitions for exercises
    val rewardType: RewardType
)

class CalculateRewardUseCase @Inject constructor(): IUseCase<CalculateRewardParams, Int> {
    override fun invoke(params: CalculateRewardParams?): Int {
        if (params == null) {
            Timber.tag("CalculateReward").e("Params cannot be null")
            return 0
        }

        when(params.rewardType) {
            RewardType.WALK -> {
                return when(params.value) {
                    in 500..1000 -> 20
                    in 1001..2000 -> 50
                    in 2001..Int.MAX_VALUE -> 100
                    else -> 0
                }
            }
            RewardType.EXERCISE -> {
                return 10
            }
            RewardType.TOTAL_WALK -> {
                return when(params.value) {
                    in 5000..10000 -> 100
                    in 10001..Int.MAX_VALUE -> 200
                    else -> 0
                }
            }
            RewardType.BONUS_EXERCISE -> {
                return 10
            }
        }
    }
}