package com.example.jetpackcompose_crudtodoapp.data

import kotlinx.coroutines.flow.Flow

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

    override fun getAllTodos(): Flow<List<TodoEntity>> {
        return todoDAO.getAllTodos()
    }
}