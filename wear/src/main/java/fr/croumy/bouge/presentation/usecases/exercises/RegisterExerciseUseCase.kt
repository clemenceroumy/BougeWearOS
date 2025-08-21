package fr.croumy.bouge.presentation.usecases.exercises

import fr.croumy.bouge.presentation.data.entities.CreditEntity
import fr.croumy.bouge.presentation.data.entities.WalkEntity
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.models.ExerciseType
import fr.croumy.bouge.presentation.models.CreditRewardType
import fr.croumy.bouge.presentation.repositories.CreditRepository
import fr.croumy.bouge.presentation.repositories.WalkRepository
import fr.croumy.bouge.presentation.usecases.credits.CalculateCreditRewardParams
import fr.croumy.bouge.presentation.usecases.credits.CalculateCreditRewardUseCase
import fr.croumy.bouge.presentation.usecases.IUseCase
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
    private val calculateCreditRewardUseCase: CalculateCreditRewardUseCase
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
                value = calculateCreditRewardUseCase(
                    CalculateCreditRewardParams(
                        value = params.steps,
                        creditRewardType = CreditRewardType.WALK
                    )
                ),
                type = ExerciseType.WALK,
                exerciseUid = walk.uid
            )
        )
    }
}