package com.example.jetpackcompose_crudtodoapp.ui.util.alarm_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.jetpackcompose_crudtodoapp.MainActivity
import com.example.jetpackcompose_crudtodoapp.R

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val i =Intent(context, MainActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val channelId = "channel"
        val channelName= "backgroundWorkerChannel"
        val notificationManager = context!!.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        lateinit var notificationChannel: NotificationChannel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
        notificationBuilder.addAction(R.drawable.ic_launcher_background, "Open NootPad", pendingIntent)
        notificationBuilder.setContentTitle("title")
        notificationBuilder.setContentText("msg")
        notificationBuilder.setAutoCancel(true)
        notificationManager.notify(100, notificationBuilder.build())


    }
}