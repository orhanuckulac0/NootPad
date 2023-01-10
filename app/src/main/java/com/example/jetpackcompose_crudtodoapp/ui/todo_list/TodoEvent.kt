package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import com.example.jetpackcompose_crudtodoapp.data.TodoEntity

sealed class TodoEvent {

    data class OnDeleteTodoClick(val todoEntity: TodoEntity): TodoEvent()
    data class OnDoneChange(val todoEntity: TodoEntity, val isDone: Boolean): TodoEvent()
    object OnUndoDeleteClick: TodoEvent()
    data class OnTodoClick(val todoEntity: TodoEntity): TodoEvent()
    object OnAddTodoClick: TodoEvent()

}
