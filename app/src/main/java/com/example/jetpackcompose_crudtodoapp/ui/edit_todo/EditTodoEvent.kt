package com.example.jetpackcompose_crudtodoapp.ui.edit_todo

sealed class EditTodoEvent {
    data class OnTitleChange(val title: String): EditTodoEvent()
    data class OnDescriptionChange(val description: String): EditTodoEvent()
    data class OnDueDateChange(val dueDate: String): EditTodoEvent()
    data class OnPriorityColorChange(val priorityColor: String): EditTodoEvent()
    object OnSaveTodoClick: EditTodoEvent()
    object OnDatePickerClick: EditTodoEvent()
}