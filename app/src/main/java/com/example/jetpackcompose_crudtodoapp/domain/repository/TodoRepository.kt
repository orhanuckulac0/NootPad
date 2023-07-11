package com.example.jetpackcompose_crudtodoapp.domain.repository

import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity

interface TodoRepository {

    suspend fun insertTodo(todoEntity: TodoEntity)
    suspend fun deleteTodo(todoEntity: TodoEntity)
    suspend fun getTodoByID(id: Int): TodoEntity?
    suspend fun deleteAllTodos()
    suspend fun getAllTodos(): List<TodoEntity>
    suspend fun getTodosByCategory(category: String): List<TodoEntity>
    suspend fun getTodoWithoutAlarmSet(): List<TodoEntity>
    suspend fun getTodoWithAlarmSet(): List<TodoEntity>

}