package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import com.example.jetpackcompose_crudtodoapp.data.TodoEntity

sealed class TodoInfoEvent {
    data class OnEditTodoClick(val todoEntity: TodoEntity): TodoInfoEvent()
}
