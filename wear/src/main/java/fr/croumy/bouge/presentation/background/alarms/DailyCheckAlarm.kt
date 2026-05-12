package fr.croumy.bouge.presentation.background.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import fr.croumy.bouge.presentation.background.broadcasts.DailyAlarmReceiver
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class DailyCheckAlarm @Inject constructor(
    val context: Context
) {
    fun scheduleDailyAlarm(isFirstSchedule: Boolean = false) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, DailyAlarmReceiver::class.java)
        val flags = if (isFirstSchedule) PendingIntent.FLAG_NO_CREATE else PendingIntent.FLAG_UPDATE_CURRENT

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            flags or PendingIntent.FLAG_IMMUTABLE
        )
        if(isFirstSchedule && pendingIntent != null) return

        val now = LocalDateTime.now()
        var targetTime = now.withHour(23).withMinute(45).withSecond(0)
        if (now.isAfter(targetTime)) targetTime = targetTime.plusDays(1)

        val triggerAtMillis = targetTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }
}
