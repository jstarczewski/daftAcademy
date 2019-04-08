package com.clakestudio.pc.homework4.main

import android.app.AlarmManager

import android.content.Intent
import androidx.core.content.ContextCompat
import com.clakestudio.pc.homework4.foreground.ScanningService
import kotlinx.android.synthetic.main.activity_main.*
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.broadcast.AppReceiver
import com.clakestudio.pc.homework4.util.ext.beforeAndroid
import com.clakestudio.pc.homework4.util.ext.fromAndroid
import com.clakestudio.pc.homework4.work.ShowChargerNotificationWorker
import java.util.*

class MainActivity : AppCompatActivity() {

    private val alarmManager: AlarmManager
        get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ContextCompat.startForegroundService(this, Intent(this, ScanningService::class.java))

        btStopService.setOnClickListener {
            stopService(Intent(this, ScanningService::class.java))
        }
        scheduleAlarm()
        scheduleSingleWork()
    }

    private fun scheduleSingleWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .build()
        val request = OneTimeWorkRequest.Builder(ShowChargerNotificationWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance()
            .enqueueUniqueWork(ShowChargerNotificationWorker::javaClass.name, ExistingWorkPolicy.APPEND, request)
    }


    private fun scheduleAlarm() {
        Intent(this, AppReceiver::class.java)
            .apply { action = "com.clakestudio.pc.homework4.NOTIFY" }
            .let { PendingIntent.getBroadcast(this, 0, it, 0) }
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
            set(Calendar.HOUR_OF_DAY, 19)
            set(Calendar.MINUTE, 58)
            set(Calendar.SECOND, 0)
        }
        if (calendar.timeInMillis - System.currentTimeMillis() <= 1)
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)
        return calendar
    }
}
