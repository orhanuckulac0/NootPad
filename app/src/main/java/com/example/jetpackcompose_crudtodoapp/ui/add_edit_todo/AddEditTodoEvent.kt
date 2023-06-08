package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo



sealed class AddEditTodoEvent {
    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    data class OnDueDateChange(val dueDate: String): AddEditTodoEvent()
    data class OnPriorityColorChange(val priorityColor: String): AddEditTodoEvent()
    data class OnCategoryChange(val category: String): AddEditTodoEvent()
    object OnDatePickerClick: AddEditTodoEvent()
    object OnSaveTodoClick: AddEditTodoEvent()
}