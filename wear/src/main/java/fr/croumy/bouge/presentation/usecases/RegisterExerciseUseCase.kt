package fr.croumy.bouge.presentation.usecases

import fr.croumy.bouge.presentation.data.entities.CreditEntity
import fr.croumy.bouge.presentation.data.entities.WalkEntity
import fr.croumy.bouge.presentation.models.ExerciseType
import fr.croumy.bouge.presentation.models.RewardType
import fr.croumy.bouge.presentation.repositories.CreditRepository
import fr.croumy.bouge.presentation.repositories.WalkRepository
import timber.log.Timber
import java.time.ZonedDateTime
import javax.inject.Inject

data class RegisterExerciseParams(
    val steps: Int,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime
)

class RegisterExerciseUseCase @Inject constructor(
    private val creditRepository: CreditRepository,
    private val walkRepository: WalkRepository,
    private val calculateRewardUseCase: CalculateRewardUseCase
) : IUseCase<RegisterExerciseParams, Unit> {

    override fun invoke(params: RegisterExerciseParams?) {
        if (params == null) {
            Timber.tag("RegisterExercise").e("Params cannot be null")
            return
        }

        Timber.tag("RegisterExercise").d("Registering exercise with params: $params")

        val walk = walkRepository.insertWalk(
            WalkEntity(
                steps = params.steps,
                start = params.startTime,
                end = params.endTime
            )
        )

        creditRepository.insertCredit(
            CreditEntity(
                value = calculateRewardUseCase(
                    CalculateRewardParams(
                        value = params.steps,
                        rewardType = RewardType.WALK
                    )
                ),
                type = ExerciseType.WALK,
                exerciseUid = walk.uid
            )
        )
    }
}