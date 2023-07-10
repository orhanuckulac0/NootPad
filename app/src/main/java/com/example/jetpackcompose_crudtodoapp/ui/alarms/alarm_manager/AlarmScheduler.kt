package com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(requestCode: Int)
}