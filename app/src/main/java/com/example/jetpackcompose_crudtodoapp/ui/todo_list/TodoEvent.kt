package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity

sealed class TodoEvent {
    data class OnDeleteTodoClick(val todoEntity: TodoEntity): TodoEvent()
    data class OnDoneChange(val todoEntity: TodoEntity, val isDone: Boolean): TodoEvent()
    data class OnTodoClick(val todoEntity: TodoEntity): TodoEvent()
    data class OnCategoryClicked(val category: String): TodoEvent()
    data class OnSetTodoToDelete(val todoEntity: TodoEntity): TodoEvent()
    object OnSettingsClicked: TodoEvent()
    object OnAddTodoClick: TodoEvent()
    object OnUndoDeleteClick: TodoEvent()
    object OnSetTodoToDeleteToNull: TodoEvent()
    object OnAlarmClicked : TodoEvent()
}
