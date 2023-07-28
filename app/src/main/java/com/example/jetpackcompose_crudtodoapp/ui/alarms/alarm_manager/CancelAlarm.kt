package com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun cancelAlarm(context: Context, requestCode: Int){
    val scheduler = AndroidAlarmScheduler(context.applicationContext)
    scheduler.cancel(requestCode)
    Log.i("MYTAG", "ALARM CANCELLED")
}