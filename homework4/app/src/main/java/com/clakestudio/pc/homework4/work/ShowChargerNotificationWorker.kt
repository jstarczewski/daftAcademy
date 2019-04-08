package com.clakestudio.pc.homework4.work

import android.content.Context
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.NotificationFactory
import com.clakestudio.pc.homework4.util.ext.fromAndroid

class ShowChargerNotificationWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun doWork(): Result {
        showChargerNotification()
        return Result.success()
    }

    private fun showChargerNotification() {
        fromAndroid(Build.VERSION_CODES.O) {
            notificationFactory.createNotificationChannels(context)
        }
        notificationFactory.show(
            context,
            context.resources.getString(R.string.notification_charger_title),
            context.resources.getString(R.string.notification_charger_message)
        )
    }

}