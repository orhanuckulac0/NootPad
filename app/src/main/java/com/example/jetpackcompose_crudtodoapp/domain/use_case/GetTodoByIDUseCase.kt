package com.example.jetpackcompose_crudtodoapp.domain.use_case

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository

class GetTodoByIDUseCase(private val repository: TodoRepository) {

    suspend fun getTodoByID(id: Int): TodoEntity?{
        return repository.getTodoByID(id)
    }

}