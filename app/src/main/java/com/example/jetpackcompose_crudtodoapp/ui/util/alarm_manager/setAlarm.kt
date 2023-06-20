package com.example.jetpackcompose_crudtodoapp.ui.util.alarm_manager

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.ui.util.calculateTimeDiff
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun setAlarm(context: Context, todo: TodoEntity){
    if (todo.isAlarmSet == false){
        val secs = calculateTimeDiff(todo.dueDate, todo.alarmDate.toString())
        val alarmItem: AlarmItem?
        alarmItem = AlarmItem(
            time = LocalDateTime.now()
                .plusSeconds(secs),
            requestCode = todo.id!!
        )

        val scheduler = AndroidAlarmScheduler(context.applicationContext)
        scheduler.schedule(alarmItem)
    }
}
