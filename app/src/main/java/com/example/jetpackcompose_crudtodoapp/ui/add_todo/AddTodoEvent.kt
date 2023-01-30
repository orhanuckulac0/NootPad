package com.example.jetpackcompose_crudtodoapp.ui.add_todo


sealed class AddTodoEvent {
    data class OnTitleChange(val title: String): AddTodoEvent()
    data class OnDescriptionChange(val description: String): AddTodoEvent()
    data class OnDueDateChange(val dueDate: String): AddTodoEvent()
    data class OnPriorityColorChange(val priorityColor: String): AddTodoEvent()
    object OnDatePickerClick: AddTodoEvent()
    object OnSaveTodoClick: AddTodoEvent()

}