package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import com.example.jetpackcompose_crudtodoapp.data.TodoEntity

sealed class TodoInfoEvent {
    object OnEditTodoClick: TodoInfoEvent()
    data class OnDeleteTodoClick(val todoEntity: TodoEntity): TodoInfoEvent()
}
