package com.example.jetpackcompose_crudtodoapp.ui.util.alarm_manager

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(requestCode: Int)
}