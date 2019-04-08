package com.clakestudio.pc.homework4.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.AlarmScheduler
import com.clakestudio.pc.homework4.util.NotificationFactory

class FamiliadaService : IntentService("FamiliadaIntentService") {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onHandleIntent(intent: Intent?) {
        showAlarmNotification(this)
        AlarmScheduler.scheduleAlarmForFamiliada(this)
    }

    private fun showAlarmNotification(context: Context) {
        notificationFactory.show(
            context,
            context.resources.getString(R.string.notification_boot_alarm_title),
            context.resources.getString(R.string.notification_boot_alarm_message)
        )
    }


}