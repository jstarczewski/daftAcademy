package com.clakestudio.pc.homework4

import android.app.Application
import android.os.Build
import com.clakestudio.pc.homework4.util.NotificationFactory
import com.clakestudio.pc.homework4.util.ext.fromAndroid

class Homework4App : Application() {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onCreate() {
        super.onCreate()
        initChannels()
    }

    private fun initChannels() {
        fromAndroid(Build.VERSION_CODES.O) { notificationFactory.createNotificationChannels(this) }
    }

}