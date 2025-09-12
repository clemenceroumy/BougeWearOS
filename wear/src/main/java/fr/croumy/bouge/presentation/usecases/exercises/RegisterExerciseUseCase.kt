package fr.croumy.bouge.presentation.usecases.exercises

import fr.croumy.bouge.presentation.data.entities.WalkEntity
import fr.croumy.bouge.presentation.models.companion.StatsUpdate
import fr.croumy.bouge.presentation.models.exercise.ExerciseType
import fr.croumy.bouge.presentation.models.credit.CreditRewardType
import fr.croumy.bouge.presentation.repositories.WalkRepository
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.usecases.credits.RegisterWonCreditsParams
import fr.croumy.bouge.presentation.usecases.credits.RegisterWonCreditsUseCase
import fr.croumy.bouge.presentation.usecases.IUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.ZonedDateTime
import javax.inject.Inject

data class RegisterExerciseParams(
    val steps: Int,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime
)

class RegisterExerciseUseCase @Inject constructor(
    private val walkRepository: WalkRepository,
    private val companionService: CompanionService,
    private val registerWonCreditsUseCase: RegisterWonCreditsUseCase
) : IUseCase<RegisterExerciseParams, Unit> {

    override fun invoke(params: RegisterExerciseParams?) {
        if (params == null) {
            Timber.tag("RegisterExercise").e("Params cannot be null")
            return
        }

        Timber.tag("RegisterExercise").d("Registering exercise with params: $params")

        CoroutineScope(Dispatchers.IO).launch {
            val walk = walkRepository.insertWalk(
                WalkEntity(
                    steps = params.steps,
                    start = params.startTime,
                    end = params.endTime
                )
            )

            registerWonCreditsUseCase(
                RegisterWonCreditsParams(
                    value = params.steps,
                    creditRewardType = CreditRewardType.WALK,
                    exerciseType = ExerciseType.WALK,
                    exerciseId = walk.uid
                )
            )

            companionService.updateHealthStat(StatsUpdate.UP(by = 1))
        }

    }
}