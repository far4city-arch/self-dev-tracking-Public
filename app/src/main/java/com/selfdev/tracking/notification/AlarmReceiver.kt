package com.selfdev.tracking.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.os.Build
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_HOUR = "extra_hour"
        const val EXTRA_MINUTE = "extra_minute"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE) ?: return
        val hour = intent.getIntExtra(EXTRA_HOUR, 8)
        val minute = intent.getIntExtra(EXTRA_MINUTE, 0)

        // أيام العمل فقط: الأحد(1) إلى الخميس(5)، تخطي الجمعة(6) والسبت(7)
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val isWorkday = today != Calendar.FRIDAY && today != Calendar.SATURDAY

        if (isWorkday) {
            when (type) {
                AlarmScheduler.TYPE_SOUND -> NotificationHelper.showQuietSoundNotification(context)
                AlarmScheduler.TYPE_VISUAL -> NotificationHelper.showVisualFlashNotification(context)
                AlarmScheduler.TYPE_VIBRATE -> triggerVibration(context)
            }
        }

        // إعادة جدولة نفس التنبيه لليوم التالي تلقائيًا
        AlarmScheduler.scheduleNextOccurrence(context, type, hour, minute)
    }

    private fun triggerVibration(context: Context) {
        val durationMs = 5000L // اهتزاز لمدة 5 ثوانٍ وفق المواصفات
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(durationMs)
            }
        }
        NotificationHelper.showVibrateNotification(context)
    }
}
