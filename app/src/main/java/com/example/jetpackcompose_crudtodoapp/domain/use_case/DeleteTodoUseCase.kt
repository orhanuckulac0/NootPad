package com.example.jetpackcompose_crudtodoapp.domain.use_case

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository

class DeleteTodoUseCase(private val repository: TodoRepository) {

    suspend fun deleteTodo(todoEntity: TodoEntity){
        repository.deleteTodo(todoEntity)
    }

}