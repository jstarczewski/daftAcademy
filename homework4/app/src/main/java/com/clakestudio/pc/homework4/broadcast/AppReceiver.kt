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
import java.util.*

class AppReceiver : BroadcastReceiver() {

    private val notificationFactory by lazy { NotificationFactory() }
    private lateinit var alarmManager: AlarmManager

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                showBootNotification(context!!)
                rescheduleAlarm(context)
            }
            "com.clakestudio.pc.homework4.NOTIFY" -> {
                showAlarmNotification(context!!)
                rescheduleAlarm(context)
            }
        }
    }

    private fun showBootNotification(context: Context) {
        notificationFactory.show(
            context,
            context.resources.getString(R.string.notification_boot_compleated_title),
            context.resources.getString(R.string.notification_boot_compleated_message)
        )
    }

    private fun showAlarmNotification(context: Context) {
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
                        getCalendarWithProperTimeSet().timeInMillis,
                        it
                    )
                }
                beforeAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        getCalendarWithProperTimeSet().timeInMillis,
                        it
                    )
                }
            }
    }

    private fun getCalendarWithProperTimeSet(): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 20)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        if (calendar.timeInMillis - System.currentTimeMillis() <= 1)
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)
        return calendar
    }
}