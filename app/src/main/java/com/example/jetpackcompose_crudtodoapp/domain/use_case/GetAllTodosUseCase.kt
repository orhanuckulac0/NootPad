package com.example.jetpackcompose_crudtodoapp.domain.use_case

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository

class GetAllTodosUseCase(private val repository: TodoRepository) {

    suspend fun getAllTodos(): List<TodoEntity>{
        return repository.getAllTodos()
    }

}