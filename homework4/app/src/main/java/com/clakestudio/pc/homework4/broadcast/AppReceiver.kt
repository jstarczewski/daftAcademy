package com.clakestudio.pc.homework4.broadcast

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.NotificationFactory
import com.clakestudio.pc.homework4.util.ext.beforeAndroid
import com.clakestudio.pc.homework4.util.ext.fromAndroid

class AppReceiver : BroadcastReceiver() {

    private val notificationFactory by lazy { NotificationFactory() }
    private lateinit var alarmManager: AlarmManager

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> showBootNotification(context!!)
            "com.clakestudio.pc.homework4.NOTIFY" -> {
                showAlarmNotification(context!!)
                rescheduleAlarm(context)
            }
        }
    }

    private fun showBootNotification(context: Context) {
        fromAndroid(Build.VERSION_CODES.O) {
            notificationFactory.createNotificationChannels(context)
        }
        notificationFactory.show(
            context,
            context.resources.getString(R.string.notification_boot_compleated_title),
            context.resources.getString(R.string.notification_boot_compleated_message)
        )
    }

    private fun showAlarmNotification(context: Context) {
        fromAndroid(Build.VERSION_CODES.O) {
            notificationFactory.createNotificationChannels(context)
        }
        notificationFactory.show(
            context,
            context.resources.getString(R.string.notification_boot_alarm_title),
            context.resources.getString(R.string.notification_boot_alarm_message)
        )
    }

    private fun rescheduleAlarm(context: Context) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Intent(context, AppReceiver::class.java)
            .apply { action = "com.clakestudio.pc.homework4.NOTIFY" }
            .let { PendingIntent.getBroadcast(context, 0, it, 0) }
            .let {
                fromAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + (10 * 60 * 1000),
                        it
                    )
                }
                beforeAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + (3600 * 24 * 1000),
                        it
                    )
                }
            }
    }
}