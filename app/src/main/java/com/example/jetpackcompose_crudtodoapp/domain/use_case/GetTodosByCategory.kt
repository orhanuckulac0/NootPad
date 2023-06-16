package com.example.jetpackcompose_crudtodoapp.domain.use_case

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository

class GetTodosByCategory(private val repository: TodoRepository) {

    suspend fun getTodosByCategory(category: String): List<TodoEntity>{
        return repository.getTodosByCategory(category)
    }

}