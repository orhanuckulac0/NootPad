package com.example.jetpackcompose_crudtodoapp.ui.alarms

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity

sealed class AlarmEvents {

    data class OnAlarmAdded(val todo: TodoEntity, val alarmTime: String): AlarmEvents()
    data class OnAlarmCancelled(val todo: TodoEntity): AlarmEvents()
    data class OnTodoDueDateChanged(val todo: TodoEntity, val newDueDate: String): AlarmEvents()

}