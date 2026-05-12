package fr.croumy.bouge.presentation.background.workers

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkQuery
import fr.croumy.bouge.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class WorkerHelper @Inject constructor(
    val context: Context
) {
    val workManager = WorkManager.getInstance(context.applicationContext)
    val hungriness_work = "decrease_hungriness"
    val happiness_work = "decrease_happiness"
    val daily_check_work = "daily_steps_check"

    init {
        if (BuildConfig.DEBUG) {
            CoroutineScope(Dispatchers.IO).launch {
                workManager.getWorkInfosFlow(
                    WorkQuery.fromUniqueWorkNames(listOf(hungriness_work, happiness_work, daily_check_work))
                ).collect {
                    it.forEach { info ->
                        Timber.tag("WorkerHelper").i("$info")
                    }
                }
            }
        }
    }

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
        workManager.enqueueUniqueWork(
            daily_check_work,
            ExistingWorkPolicy.KEEP,
            DailyCheckWorker.setupWork
        )
    }

    fun pauseCompanionStatsWorker() {
        WorkManager
            .getInstance(context.applicationContext)
            .cancelUniqueWork("decrease_hungriness")

        WorkManager
            .getInstance(context.applicationContext)
            .cancelUniqueWork("decrease_happiness")
    }

    fun resumeCompanionStatsWorker() {
        launchHungrinessWorker(ExistingWorkPolicy.KEEP)
        launchHappinessWorker(ExistingWorkPolicy.KEEP)
    }
}