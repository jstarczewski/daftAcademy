package com.clakestudio.pc.homework4.broadcast

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.ext.fromAndroid
import com.clakestudio.pc.homework4.util.NotificationFactory
import com.clakestudio.pc.homework4.util.ext.beforeAndroid
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {

    private val notificationFactory by lazy { NotificationFactory() }
    private lateinit var alarmManager: AlarmManager

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            "com.clakestudio.pc.homework4.NOTIFY" -> {
                showAlarmNotification(context!!)
                rescheduleAlarm(context!!)
            }
        }
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
        Intent(context, AlarmReceiver::class.java)
            .apply { action = "com.clakestudio.pc.homework4.NOTIFY" }
            .let { PendingIntent.getBroadcast(context, 0, it, 0) }
            .let {
                fromAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20),
                        it
                    )
                }
                beforeAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20),
                        it
                    )
                }
            }
    }
}