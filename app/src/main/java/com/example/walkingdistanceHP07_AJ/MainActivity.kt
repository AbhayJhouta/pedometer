package com.example.walkingdistanceHP07_AJ

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ALL
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private var stepsen: Sensor? = null
    val ACTIVITY_RECOGNITION_REQUEST_CODE=100
    var pre:Long=0;
    lateinit var intenlt:Intent
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Actvitypermission()
        nchannelcreate()
        createservice()
        getcursteps()

    }
    fun getcursteps()
    { val shrd=getSharedPreferences("store", MODE_PRIVATE)
        val viewmodel=ViewModelProvider(this,viewmodelfactory(shrd)).get(MViewModel::class.java)
        val steps=viewmodel.getsteps();
        val pbar=findViewById<ProgressBar>(R.id.progressBar)
        pbar.max=100;
        //pbar.progress=steps
        pbar.setProgress(steps)


    }
    fun createservice()
    {
        intenlt= Intent(this,foregroundservice::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(intenlt)
        }
    }
    fun Actvitypermission()
    { if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
        != PackageManager.PERMISSION_GRANTED) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNITION_REQUEST_CODE
            )
        }
    }

    }
    fun nchannelcreate()
    {  val channel:NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            channel = NotificationChannel("2", "ffg", importance).apply {
                description = "dss"
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}