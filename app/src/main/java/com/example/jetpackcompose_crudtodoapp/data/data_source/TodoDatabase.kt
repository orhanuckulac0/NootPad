package com.example.jetpackcompose_crudtodoapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {

    abstract val todoDAO: TodoDAO

}