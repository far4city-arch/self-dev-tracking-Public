package com.selfdev.tracking.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

/**
 * يجدول ثلاثة تنبيهات يومية في أيام العمل وفق المواصفات:
 * 1) الساعة 8:00 صباحًا: إشعار صوتي هادئ
 * 2) الساعة 10:00 صباحًا: إشعار بصري (وميض في الشاشة الرئيسية)
 * 3) الساعة 12:00 ظهرًا: تنبيه اهتزاز لمدة 5 ثوانٍ
 *
 * أيام العمل في قطر: الأحد إلى الخميس (الجمعة والسبت عطلة)، ويتم تخطيهما داخل [AlarmReceiver].
 */
object AlarmScheduler {

    const val TYPE_SOUND = "TYPE_SOUND"
    const val TYPE_VISUAL = "TYPE_VISUAL"
    const val TYPE_VIBRATE = "TYPE_VIBRATE"

    private const val REQUEST_SOUND = 1001
    private const val REQUEST_VISUAL = 1002
    private const val REQUEST_VIBRATE = 1003

    fun scheduleAll(context: Context) {
        schedule(context, hour = 8, minute = 0, type = TYPE_SOUND, requestCode = REQUEST_SOUND)
        schedule(context, hour = 10, minute = 0, type = TYPE_VISUAL, requestCode = REQUEST_VISUAL)
        schedule(context, hour = 12, minute = 0, type = TYPE_VIBRATE, requestCode = REQUEST_VIBRATE)
    }

    fun scheduleNextOccurrence(context: Context, type: String, hour: Int, minute: Int) {
        val requestCode = when (type) {
            TYPE_SOUND -> REQUEST_SOUND
            TYPE_VISUAL -> REQUEST_VISUAL
            else -> REQUEST_VIBRATE
        }
        schedule(context, hour, minute, type, requestCode, startFromTomorrow = true)
    }

    private fun schedule(
        context: Context,
        hour: Int,
        minute: Int,
        type: String,
        requestCode: Int,
        startFromTomorrow: Boolean = false
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (startFromTomorrow || before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.EXTRA_TYPE, type)
            putExtra(AlarmReceiver.EXTRA_HOUR, hour)
            putExtra(AlarmReceiver.EXTRA_MINUTE, minute)
        }

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, flags)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                )
            } else {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
            )
        }
    }
}
