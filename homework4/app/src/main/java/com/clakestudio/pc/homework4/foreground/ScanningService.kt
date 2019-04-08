package com.clakestudio.pc.homework4.foreground

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.clakestudio.pc.homework4.util.NotificationFactory

private const val NOTIFICATION_ID = 997
private const val NOTIFICATION_TITLE = "Skanowanie"
private const val NOTIFICATION_BODY = "Skanowanie beconów aktywne..."

class ScanningService : Service() {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, notificationFactory.create(this, NOTIFICATION_TITLE, NOTIFICATION_BODY))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showStartsToast()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun showStartsToast() {
        Toast.makeText(this, "Scanning... ", Toast.LENGTH_SHORT).show()
    }
}