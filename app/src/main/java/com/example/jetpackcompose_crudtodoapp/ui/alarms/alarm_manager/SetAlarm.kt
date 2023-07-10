package com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun setAlarm(context: Context, todoId: Int, dueDate: String, alarmDate: String){
    val secs = calculateTimeDiff(dueDate, alarmDate)
    val alarmItem: AlarmItem?
    alarmItem = AlarmItem(
        time = LocalDateTime.now()
            .plusSeconds(secs),
        requestCode = todoId
    )

    val scheduler = AndroidAlarmScheduler(context.applicationContext)
    scheduler.schedule(alarmItem)
}
