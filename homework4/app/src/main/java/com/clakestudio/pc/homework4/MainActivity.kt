package com.clakestudio.pc.homework4

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clakestudio.pc.homework4.broadcast.AlarmReceiver
import com.clakestudio.pc.homework4.util.ext.beforeAndroid
import com.clakestudio.pc.homework4.util.ext.fromAndroid
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val alarmManager: AlarmManager
        get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scheduleSingleAlarm()
    }


    private fun scheduleSingleAlarm() {
        Intent(this, AlarmReceiver::class.java)
            .apply { action = "com.clakestudio.pc.homework4.NOTIFY" }
            .let { PendingIntent.getBroadcast(this, 0, it, 0) }
            .let {
                fromAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(4),
                        it)
                }
                beforeAndroid(Build.VERSION_CODES.M) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(4),
                        it)
                }
            }
    }
}
