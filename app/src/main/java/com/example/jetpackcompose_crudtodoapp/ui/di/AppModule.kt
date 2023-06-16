package com.example.jetpackcompose_crudtodoapp.ui.di

import android.app.Application
import androidx.room.Room
import com.example.jetpackcompose_crudtodoapp.data.data_source.TodoDatabase
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository
import com.example.jetpackcompose_crudtodoapp.data.repository.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoDAO)
    }
}