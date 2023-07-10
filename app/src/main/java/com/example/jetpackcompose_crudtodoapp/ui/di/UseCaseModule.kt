package com.example.jetpackcompose_crudtodoapp.ui.di

import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository
import com.example.jetpackcompose_crudtodoapp.domain.use_case.AddEditTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.DeleteTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetAllTodosUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodoWithoutAlarmSet
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodoByIDUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodosByCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideAddEditTodoUseCase(repository: TodoRepository): AddEditTodoUseCase{
        return AddEditTodoUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteTodoUseCase(repository: TodoRepository): DeleteTodoUseCase{
        return DeleteTodoUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllTodosUseCase(repository: TodoRepository): GetAllTodosUseCase{
        return GetAllTodosUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetTodosByCategoryUseCase(repository: TodoRepository): GetTodosByCategory{
        return GetTodosByCategory(repository)
    }

    @Singleton
    @Provides
    fun provideGetTodoByIDUseCase(repository: TodoRepository): GetTodoByIDUseCase{
        return GetTodoByIDUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetTodoWithoutAlarmSet(repository: TodoRepository): GetTodoWithoutAlarmSet{
        return GetTodoWithoutAlarmSet(repository)
    }
}