package com.example.jetpackcompose_crudtodoapp.domain.use_case

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository

class GetTodoWithoutAlarmSet(private val repository: TodoRepository) {

    suspend fun getTodoWithoutAlarmSet(): List<TodoEntity> {
        return repository.getTodoWithoutAlarmSet()
    }

}