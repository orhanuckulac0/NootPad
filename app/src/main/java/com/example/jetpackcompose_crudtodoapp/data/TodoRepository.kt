package com.example.jetpackcompose_crudtodoapp.data

import kotlinx.coroutines.flow.Flow

// Repository's job is to access to all of the data sources and
// then is to decide which data it should forward to the view model
interface TodoRepository {

    suspend fun insertTodo(todoEntity: TodoEntity)
    suspend fun deleteTodo(todoEntity: TodoEntity)
    suspend fun getTodoByID(id: Int): TodoEntity?
    suspend fun deleteAllTodos()
    fun getAllTodos(): Flow<List<TodoEntity>>

}