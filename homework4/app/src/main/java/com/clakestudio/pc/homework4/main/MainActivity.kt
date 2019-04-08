package com.clakestudio.pc.homework4.main

import android.content.Intent
import androidx.core.content.ContextCompat
import com.clakestudio.pc.homework4.services.ScanningService
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.AlarmScheduler
import com.clakestudio.pc.homework4.work.ShowChargerNotificationWorker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStopService.setOnClickListener {
            stopService(Intent(this, ScanningService::class.java))
        }

        scheduleAlarm()

        /**
         * Jeżeli dobrze zrozumiałem dyskusje na Slacku to "po pierwszym uruchomieniu i przy pierwszym podlaczeniu ladowarki" oznacza z każdym
         * następnym uruchomieniem aplikacji nadpisujemy poprzednie powiadomienie
         * i zlecamy prace do wykonania jeszcze raz, która ma sie wykonać tylko jeden raz (REPLACE nie APPEND) ?
         * */

        scheduleSingleWork()
    }

    override fun onStart() {
        super.onStart()
        startForegroundService()
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
            .enqueueUniqueWork(ShowChargerNotificationWorker::javaClass.name, ExistingWorkPolicy.REPLACE, request)
    }

    private fun scheduleAlarm() = AlarmScheduler.scheduleAlarmForFamiliada(this)

    private fun startForegroundService() =
        ContextCompat.startForegroundService(this, Intent(this, ScanningService::class.java))
}
