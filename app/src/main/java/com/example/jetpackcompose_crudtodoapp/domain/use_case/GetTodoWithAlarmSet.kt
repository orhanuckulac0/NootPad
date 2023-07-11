package com.example.jetpackcompose_crudtodoapp.domain.use_case

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository

class GetTodoWithAlarmSet(private val repository: TodoRepository) {

    suspend fun getTodoWithAlarmSet(): List<TodoEntity> {
        return repository.getTodoWithAlarmSet()
    }

}