package com.example.walkingdistanceHP07_AJ

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class foregroundservice:Service(), SensorEventListener {
    var cur=0
    private lateinit var sensorManager: SensorManager
    private var stepsen: Sensor? = null
    lateinit var stintent:Intent
    var stflaf:Int = 0;
    var stid:Int = 0;
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepsen = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        if(stepsen==null)
            Toast.makeText(this,"null",Toast.LENGTH_LONG).show()
        sensorManager.registerListener(this, stepsen,SensorManager.SENSOR_DELAY_NORMAL)
       // sensorManager.unregisterListener(this)
        val shrd=getSharedPreferences("store", MODE_PRIVATE)
        val ed=shrd.edit()
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        ed.putString("date",currentDate)
        ed.apply()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            stintent=intent
        }
        stflaf=flags
        stid=startId
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        var noty = NotificationCompat.Builder(this, "2")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(cur.toString()+" steps today")
            .setContentText("need to walk more")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).setSilent(true).build()
        startForeground(3, noty)
        return Service.START_STICKY

    }

    override  fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Toast.makeText(MainActivity@this,"d",Toast.LENGTH_LONG).show()
    }
     override fun onSensorChanged(event: SensorEvent?) {
        // val lux = event?.values?.get(0)
         //val time = event?.timestamp
         //  Toast.makeText(this, lux.toString(), Toast.LENGTH_LONG).show()
         val sdf = SimpleDateFormat("dd/M/yyyy")
         val currentDate = sdf.format(Date())
         val shrd=getSharedPreferences("store", MODE_PRIVATE)
         val date=shrd.getString("date","0");
         Log.d("date",date+"--"+currentDate)
         if(currentDate!=date)
         {
             cur=0;
             shrd.edit().putString("date",currentDate).apply()
         }
         else
         {
             cur++;
         }
         shrd.edit().putInt("cur",cur).apply()
             CoroutineScope(Dispatchers.Default).launch {

                 // Toast.makeText(MainActivity@ this, cur, Toast.LENGTH_LONG).show()
                 onStartCommand(stintent, stflaf, stid)
             }

    }


}