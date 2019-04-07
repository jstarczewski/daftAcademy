package com.clakestudio.pc.homework4

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clakestudio.pc.homework4.broadcast.AlarmReceier

class MainActivity : AppCompatActivity() {


    private val alarmManager: AlarmManager
        get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        schedulePeriodicAlarm()
    }


    private fun schedulePeriodicAlarm() {
        Intent(this, AlarmReceier::class.java)
            .apply { action = "com.clakestudio.pc.homework4.NOTIFY" }
            .let { PendingIntent.getBroadcast(this, 0, it, 0) }
            .let {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    it)
            }
    }
}
