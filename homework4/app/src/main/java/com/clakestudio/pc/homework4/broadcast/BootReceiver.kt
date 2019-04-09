package com.clakestudio.pc.homework4.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.clakestudio.pc.homework4.R
import com.clakestudio.pc.homework4.util.AlarmScheduler
import com.clakestudio.pc.homework4.util.NotificationFactory

class BootReceiver : BroadcastReceiver() {

    /**
     * Ustawiamy alarm na 16:20 po bootowaniu jeszcze raz, bo miał działać codziennie od pierwszego odpalenia apki
     *
     * BootReceiver może nie dzialać jeżeli byłoby to testowane na Huaweiach (np. P9 Lite), bo apka
     * nie moze zostać dodana do auto-startu (Od nakładki EMUI na API lvl 23 i wyżej chyba).
     *
     * https://stackoverflow.com/questions/43913937/intent-boot-completed-not-working-on-huawei-device
     *
     * BootReceiver zadziałał na emulatorze Pixel 2
     * */

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