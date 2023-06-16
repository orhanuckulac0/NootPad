package com.example.jetpackcompose_crudtodoapp.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity

@Dao
interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM todo_table WHERE id=:id")
    suspend fun getTodoByID(id: Int): TodoEntity?

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodos()

    @Query("SELECT * FROM todo_table")
    suspend fun getAllTodos(): List<TodoEntity>

    @Query("SELECT * FROM todo_table WHERE category=:category")
    suspend fun getTodosByCategory(category: String): List<TodoEntity>
}