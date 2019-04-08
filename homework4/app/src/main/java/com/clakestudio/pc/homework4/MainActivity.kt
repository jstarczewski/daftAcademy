package com.clakestudio.pc.homework4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import com.clakestudio.pc.homework4.util.NotificationFactory
=======
import androidx.core.content.ContextCompat
import com.clakestudio.pc.homework4.foreground.ScanningService
import kotlinx.android.synthetic.main.activity_main.*
>>>>>>> feature_foreground

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ContextCompat.startForegroundService(this, Intent(this, ScanningService::class.java))

        btStopService.setOnClickListener {
            stopService(Intent(this, ScanningService::class.java))
        }

    }
}
