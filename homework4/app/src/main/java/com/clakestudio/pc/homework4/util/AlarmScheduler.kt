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

    /**
     * Postanowilem reschedulowac alarm z ta sama godzina i flaga UPDATE CURRENT (chociaż w apce jest jeden alarm takze
     * flaga moglaby nie zmieniac juz setowanego alarmu) kiedy sie wykona aby uzyskać
     * (chyba) największą mozliwą dokładność. W dokumentacji znalazłem fragment, ze od Androida KitKat wszystkie alarmy ustawione jako
     * setRepeating sa niedokładne, a obecnie wiekszość urządzeń dziala na API >= 19
     * Reschedulowanie polecili we fragmencie Note do metody setRepeating, plus taki ze alarm w Doze/Standby zadziala dokladnie
     *
     * https://developer.android.com/reference/android/app/AlarmManager.html#setRepeating(int,%20long,%20long,%20android.app.PendingIntent)
     *
     * */

    fun scheduleAlarmForFamiliada(context: Context) =
        rescheduleAlarm(context, getCalendarWithProperTimeSetForFamiliada())

    private fun rescheduleAlarm(context: Context, calendar: Calendar) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Intent(context, FamiliadaService::class.java)
            .apply { action = "com.clakestudio.pc.homework4.NOTIFY" }
            .let { PendingIntent.getService(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
            .let {
                fromAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        it
                    )
                }
                beforeAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        it
                    )
                }
            }
    }

    private fun getCalendarWithProperTimeSetForFamiliada(): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 16)
            set(Calendar.MINUTE, 20)
            set(Calendar.SECOND, 0)
        }
        if (calendar.timeInMillis - System.currentTimeMillis() <= 1)
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)
        return calendar
    }

}