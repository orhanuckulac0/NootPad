package com.example.jetpackcompose_crudtodoapp.ui.util

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
    object ShowDatePicker: UiEvent()
    object ShowTimePicker: UiEvent()
    object CancelAlarm: UiEvent()
}