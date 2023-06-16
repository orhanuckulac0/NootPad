package com.example.jetpackcompose_crudtodoapp.domain.use_case

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository

class AddEditTodoUseCase(
    private val todoRepository: TodoRepository){

    suspend fun addEditTodo(todoEntity: TodoEntity){
        todoRepository.insertTodo(todoEntity)
    }
}