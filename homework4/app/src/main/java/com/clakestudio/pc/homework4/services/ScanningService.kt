package com.clakestudio.pc.homework4.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.NotificationFactory

private const val NOTIFICATION_ID = 997

class ScanningService : Service() {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onCreate() {
        super.onCreate()
        startForeground(
            NOTIFICATION_ID, notificationFactory.create(
                this,
                getString(R.string.notification_scanning_title),
                getString(R.string.notification_scanning_message)
            )
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showStartsToast()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun showStartsToast() =
        Toast.makeText(this, getString(R.string.foreground_service_toast_text), Toast.LENGTH_SHORT).show()
}