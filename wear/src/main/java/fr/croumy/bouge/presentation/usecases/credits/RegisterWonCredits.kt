package fr.croumy.bouge.presentation.usecases.credits

import fr.croumy.bouge.presentation.data.entities.CreditEntity
import fr.croumy.bouge.presentation.models.credit.CreditRewardType
import fr.croumy.bouge.presentation.models.exercise.ExerciseType
import fr.croumy.bouge.presentation.repositories.CreditRepository
import fr.croumy.bouge.presentation.usecases.IUseCase
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

data class RegisterWonCreditsParams(
    val value: Int, // steps for walks, repetitions for exercises
    val creditRewardType: CreditRewardType,
    val exerciseType: ExerciseType? = null, // only for exercises
    val exerciseId: UUID? = null
)

class RegisterWonCredits @Inject constructor(
    private val creditRepository: CreditRepository
): IUseCase<RegisterWonCreditsParams, Unit> {
    override fun invoke(params: RegisterWonCreditsParams?): Unit {
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
            CreditRewardType.TOTAL_WALK -> {
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

        creditRepository.insertCredit(
            CreditEntity(
                value = calculatedCredits,
                type = params.exerciseType,
                exerciseUid = params.exerciseId
            )
        )
    }
}