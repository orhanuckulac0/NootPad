package com.example.jetpackcompose_crudtodoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String,
    val isDone: Boolean,
//    val dueDate: String,
//    val priorityColor: String
)