package com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun calculateTimeDiff(date: String, time: String): Long {
    val concatenate = "$date $time"
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm", Locale.ENGLISH)
    val targetTime = ZonedDateTime.parse(concatenate, formatter.withZone(ZoneId.systemDefault()))
    val currentTime = ZonedDateTime.now(ZoneId.systemDefault())

    val secondsDifference = ChronoUnit.SECONDS.between(currentTime, targetTime)
    return secondsDifference.coerceAtLeast(0) // Ensure non-negative difference
}