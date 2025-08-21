package fr.croumy.bouge.presentation.usecases.exercises

import android.os.SystemClock
import androidx.health.services.client.data.IntervalDataPoint
import fr.croumy.bouge.presentation.models.Constants
import fr.croumy.bouge.presentation.services.DataService
import fr.croumy.bouge.presentation.usecases.IUseCase
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.time.toKotlinDuration

data class ConvertStepsToWalkUseCaseParams(
    val dataPoints: List<IntervalDataPoint<Long>>
)

class ConvertStepsToWalkUseCase @Inject constructor(
    val dataService: DataService,
    val registerExerciseUseCase: RegisterExerciseUseCase
) : IUseCase<ConvertStepsToWalkUseCaseParams, Unit> {

    override fun invoke(params: ConvertStepsToWalkUseCaseParams?) {
        val bootInstant = Instant.ofEpochMilli(System.currentTimeMillis() - SystemClock.elapsedRealtime())

        val dataPointStepsWithTime = params?.dataPoints?.map { dataPoint ->
            val stepTime = dataPoint.getEndInstant(bootInstant)
            Pair(stepTime, dataPoint)
        } ?: emptyList()

        if(dataPointStepsWithTime.isNotEmpty()) dataService.setIsWalking(true)

        dataPointStepsWithTime.forEachIndexed { index, pair ->
            val stepTime: Instant = pair.first
            val dataPoint = pair.second

            dataService.lastStepTime.value = ZonedDateTime.ofInstant(
                stepTime,
                ZoneId.systemDefault()
            )

            val previousStepInstant: Instant? = dataPointStepsWithTime.getOrNull(index - 1)?.first
            val durationBetweenPreviousStep = Duration.between(
                previousStepInstant ?: dataService.lastStepTime.value.toInstant(),
                stepTime
            ).toKotlinDuration()

            if (durationBetweenPreviousStep < Constants.TIME_GAP_BETWEEN_WALKS) {
                if (dataService.currentWalk.value == 0) dataService.firstStepTime.value = dataService.lastStepTime.value

                dataService.setCurrentWalk(dataService.currentWalk.value + dataPoint.value.toInt())
            } else {
                // IN CASE STEPS ARE RECEIVED IN THE SAME BATCH BUT WITH A GAP
                if (dataService.currentWalk.value > Constants.MINIMUM_STEPS_WALK) {
                    registerExerciseUseCase(
                        RegisterExerciseParams(
                            steps = dataService.currentWalk.value,
                            startTime = dataService.firstStepTime.value,
                            endTime = dataService.lastStepTime.value
                        )
                    )
                }
                dataService.firstStepTime.value = dataService.lastStepTime.value
                dataService.setCurrentWalk(dataPoint.value.toInt())
            }
        }
    }
}