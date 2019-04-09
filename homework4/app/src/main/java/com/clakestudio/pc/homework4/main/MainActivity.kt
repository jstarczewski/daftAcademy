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
         * Jeżeli dobrze zrozumiałem dyskusje na Slacku to "po pierwszym uruchomieniu i przy pierwszym podlaczeniu ladowarki gdy jest internet"
         * oznacza z każdym następnym uruchomieniem aplikacji nie dodajemy kolejnej pracy jezeli taka jest juz zlecona ?
         * */

        scheduleSingleWork()
    }

    override fun onStart() {
        super.onStart()

        /**
         * Wzbudzamy w onStart, bo nie ma przycisku, a tak latwo widac toast z onStartCommand
         * */

        startForegroundService()
    }

    private fun scheduleSingleWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
        val request = OneTimeWorkRequest.Builder(ShowChargerNotificationWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance()
            .enqueueUniqueWork(ShowChargerNotificationWorker::javaClass.name, ExistingWorkPolicy.KEEP, request)
    }

    private fun scheduleAlarm() = AlarmScheduler.scheduleAlarmForFamiliada(this)

    private fun startForegroundService() =
        ContextCompat.startForegroundService(this, Intent(this, ScanningService::class.java))
}
