package com.selfdev.tracking.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.selfdev.tracking.MainActivity
import com.selfdev.tracking.R

object NotificationHelper {

    private const val CHANNEL_ID = "self_dev_tracking_channel"
    private const val NOTIF_SOUND = 2001
    private const val NOTIF_VISUAL = 2002
    private const val NOTIF_VIBRATE = 2003

    private fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    // صوت هادئ حسب المطلوب وليس صوت تنبيه صاخب
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                }
                manager.createNotificationChannel(channel)
            }
        }
    }

    private fun openAppIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun showQuietSoundNotification(context: Context) {
        ensureChannel(context)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notif_morning_reminder))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(openAppIntent(context))
            .setAutoCancel(true)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(NOTIF_SOUND, notification)
    }

    fun showVisualFlashNotification(context: Context) {
        ensureChannel(context)
        // إشعار بصري بدون صوت، تُترجم فعليًا وميضًا في الشاشة الرئيسية عبر MainActivity
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notif_visual_reminder))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSilent(true)
            .setContentIntent(openAppIntent(context))
            .setAutoCancel(true)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(NOTIF_VISUAL, notification)

        // علم يقرأه MainActivity عند فتحه ليعرض تأثير وميض فعلي على الشاشة الرئيسية للتطبيق
        FlashSignal.pending = true
    }

    fun showVibrateNotification(context: Context) {
        ensureChannel(context)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notif_noon_reminder))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSilent(true)
            .setContentIntent(openAppIntent(context))
            .setAutoCancel(true)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(NOTIF_VIBRATE, notification)
    }
}

/** علامة بسيطة داخل الذاكرة تُستخدم لتشغيل تأثير الوميض عند فتح الشاشة الرئيسية */
object FlashSignal {
    var pending: Boolean = false
}
