package fr.croumy.bouge.presentation.background.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.presentation.background.alarms.DailyCheckAlarm
import fr.croumy.bouge.presentation.background.workers.WorkerHelper
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DailyAlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var workerHelper: WorkerHelper
    @Inject
    lateinit var dailyCheckAlarm: DailyCheckAlarm

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {

            }
            else -> {
                Timber.tag("DailyAlarmReceiver").i("Daily alarm received, launching daily worker and rescheduling alarm")
                workerHelper.launchDailyWorker()

                dailyCheckAlarm.scheduleDailyAlarm()
            }
        }
    }
}