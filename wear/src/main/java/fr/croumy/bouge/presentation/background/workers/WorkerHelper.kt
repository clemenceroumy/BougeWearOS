package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkQuery
import fr.croumy.bouge.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerHelper @Inject constructor(
    val context: Context
) {
    val workManager = WorkManager.getInstance(context.applicationContext)
    val hungriness_work = "decrease_hungriness"
    val happiness_work = "decrease_happiness"
    val daily_check_work = "daily_steps_check"

    fun launchHungrinessWorker(
        policy: ExistingWorkPolicy = ExistingWorkPolicy.REPLACE
    ) {
        Timber.tag("WorkerHelper").i("launchHungrinessWorker()")
        workManager
            .enqueueUniqueWork(
                hungriness_work,
                policy,
                HungrinessWorker.setupWork
            )
    }

    fun launchHappinessWorker(
        policy: ExistingWorkPolicy = ExistingWorkPolicy.REPLACE
    ) {
        Timber.tag("WorkerHelper").i("launchHappinessWorker()")
        workManager.enqueueUniqueWork(
            happiness_work,
            policy,
            HappinessWorker.setupWork
        )
    }

    fun launchDailyWorker() {
        Timber.tag("WorkerHelper").i("launchDailyWorker()")
        workManager.enqueueUniquePeriodicWork(
            daily_check_work,
            ExistingPeriodicWorkPolicy.KEEP,
            DailyCheckWorker.setupWork
        )
    }

    fun pauseCompanionStatsWorker() {
        WorkManager
            .getInstance(context.applicationContext)
            .cancelUniqueWork(hungriness_work)

        WorkManager
            .getInstance(context.applicationContext)
            .cancelUniqueWork(happiness_work)
    }

    fun resumeCompanionStatsWorker() {
        launchHungrinessWorker(ExistingWorkPolicy.KEEP)
        launchHappinessWorker(ExistingWorkPolicy.KEEP)
    }
}