package com.example.jetpackcompose_crudtodoapp.data.repository

import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository
import com.example.jetpackcompose_crudtodoapp.data.data_source.TodoDAO
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity

class TodoRepositoryImpl(private val todoDAO: TodoDAO): TodoRepository {
    override suspend fun insertTodo(todoEntity: TodoEntity) {
        todoDAO.insertTodo(todoEntity)
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        todoDAO.deleteTodo(todoEntity)
    }

    override suspend fun getTodoByID(id: Int): TodoEntity? {
        return todoDAO.getTodoByID(id)
    }

    override suspend fun deleteAllTodos() {
        todoDAO.deleteAllTodos()
    }

    override suspend fun getAllTodos(): List<TodoEntity> {
        return todoDAO.getAllTodos()
    }

    override suspend fun getTodosByCategory(category: String): List<TodoEntity> {
        return todoDAO.getTodosByCategory(category)
    }

    override suspend fun getTodoWithoutAlarmSet(): List<TodoEntity> {
        return todoDAO.getTodoWithoutAlarmSet()
    }

    override suspend fun getTodoWithAlarmSet(): List<TodoEntity> {
        return todoDAO.getTodoWithAlarmSet()
    }
}