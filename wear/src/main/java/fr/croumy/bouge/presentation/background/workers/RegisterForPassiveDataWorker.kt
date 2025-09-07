package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.croumy.bouge.presentation.services.HealthService
import timber.log.Timber
import javax.inject.Inject

@HiltWorker
class RegisterForPassiveDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val healthService: HealthService
): CoroutineWorker(appContext, workerParams) {
     override suspend fun doWork(): Result {
        //try {
         //TODO:
         // "android.app.ForegroundServiceStartNotAllowedException: startForegroundService() not allowed due to mAllowStartForeground false" error
         // Because Android 14+ restrictions, starting foreground services that need "while-in-use" permission is not allowed
         // cf. https://developer.android.com/develop/background-work/services/fgs/restrictions-bg-start#wiu-restrictions-exemptions
            healthService.initService()
            Timber.i("HealthService initialized from RegisterForPassiveDataWorker")
        /*} catch (e: Exception) {
            // MIGHT BE BECAUSE PERMISSIONS ARE NOT GRANTED
            // IN THAT CASE, NOTHING NEEDS TO HAPPEN
            Timber.e(e)
        }*/

        return Result.success()
    }

    companion object {
        val setupWork = OneTimeWorkRequestBuilder<RegisterForPassiveDataWorker>().build()
    }
}