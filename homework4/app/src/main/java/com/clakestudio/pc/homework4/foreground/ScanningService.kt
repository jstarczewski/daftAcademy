package com.clakestudio.pc.homework4.foreground

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.clakestudio.pc.homework4.util.NotificationFactory

private const val NOTIFICATION_ID = 997
private const val NOTIFICATION_TITLE = "Service Info"
private const val NOTIFICATION_BODY = "Service running..."

class ScanningService : Service() {

    private val TAG = "ScanningService"
    private val notificationFactory by lazy { NotificationFactory() }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onCreate() {
        super.onCreate()

        startForeground(NOTIFICATION_ID, notificationFactory.create(this, NOTIFICATION_TITLE, NOTIFICATION_BODY))
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }


}