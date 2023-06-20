package com.example.jetpackcompose_crudtodoapp.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun calculateTimeDiff(
    date: String,
    time: String
): Long {
    val concatenate  = "$date $time"
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm", Locale.ENGLISH)
    val targetTime = LocalDateTime.parse(concatenate, formatter)
    val currentTime = LocalDateTime.now()

    val secondsDifference = ChronoUnit.SECONDS.between(currentTime, targetTime)
    println("Time difference in seconds: $secondsDifference")
    return secondsDifference

}