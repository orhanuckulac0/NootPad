package com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val requestCode: Int
)
