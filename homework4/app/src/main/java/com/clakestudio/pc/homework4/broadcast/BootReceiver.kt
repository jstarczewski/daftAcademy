package com.clakestudio.pc.homework4.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.AlarmScheduler
import com.clakestudio.pc.homework4.util.NotificationFactory

class BootReceiver : BroadcastReceiver() {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                showBootNotification(context!!)
                AlarmScheduler.scheduleAlarmForFamiliada(context)
            }
        }
    }

    private fun showBootNotification(context: Context) {
        notificationFactory.show(
            context,
            context.resources.getString(R.string.notification_boot_compleated_title),
            context.resources.getString(R.string.notification_boot_compleated_message)
        )
    }

}