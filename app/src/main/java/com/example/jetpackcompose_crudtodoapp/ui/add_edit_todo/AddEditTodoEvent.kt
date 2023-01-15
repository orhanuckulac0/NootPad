package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

sealed class AddEditTodoEvent {
    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    data class OnDueDateChange(val dueDate: String): AddEditTodoEvent()
    data class OnPriorityColorChange(val priorityColor: String): AddEditTodoEvent()
    object OnSaveTodoClick: AddEditTodoEvent()
    object OnDatePickerClick: AddEditTodoEvent()
}