package com.example.jetpackcompose_crudtodoapp.ui.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun isPastDate(dateString: String): Boolean{
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
    val date = LocalDate.parse(dateString, formatter)

    return date.isBefore(LocalDate.now())
}