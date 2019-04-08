package com.clakestudio.pc.homework4.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.clakestudio.pc.homework4.services.FamiliadaService
import com.clakestudio.pc.homework4.util.ext.beforeAndroid
import com.clakestudio.pc.homework4.util.ext.fromAndroid
import java.util.*

object AlarmScheduler {

    fun scheduleAlarmForFamiliada(context: Context) =
        rescheduleAlarm(context)

    private fun rescheduleAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Intent(context, FamiliadaService::class.java)
            .apply { action = "com.clakestudio.pc.homework4.NOTIFY" }
            .let { PendingIntent.getService(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
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
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 16)
            set(Calendar.SECOND, 0)
        }
        if (calendar.timeInMillis - System.currentTimeMillis() <= 1)
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)
        return calendar
    }

}