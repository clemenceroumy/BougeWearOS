package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.croumy.bouge.presentation.services.HealthService
import timber.log.Timber

@HiltWorker
class RegisterForPassiveDataWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val healthService: HealthService
): Worker(appContext, workerParams) {

    override fun doWork(): Result {
        try
        {
            healthService.initService()
        } catch (e: Exception) {
            // MIGHT BE BECAUSE PERMISSIONS ARE NOT GRANTED
            // IN THAT CASE, NOTHING NEEDS TO HAPPEN
            Timber.e(e)
        }

        return Result.success()
    }
}