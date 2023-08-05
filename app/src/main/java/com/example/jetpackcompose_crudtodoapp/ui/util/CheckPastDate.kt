package com.example.jetpackcompose_crudtodoapp.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun isPastDate(dateString: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
    val dateParts = dateString.split(" ")

    val day = dateParts[0].padStart(2, '0') // Add leading zero if needed
    val month = dateParts[1]
    val year = dateParts[2]

    val formattedDateString = "$day $month $year"

    val date = LocalDate.parse(formattedDateString, formatter)

    return date.isBefore(LocalDate.now())
}