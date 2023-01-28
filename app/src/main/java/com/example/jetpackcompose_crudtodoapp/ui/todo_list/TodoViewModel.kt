package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.data.TodoEntity
import com.example.jetpackcompose_crudtodoapp.data.TodoRepository
import com.example.jetpackcompose_crudtodoapp.util.Routes
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    val todos = repository.getAllTodos()

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var deletedTodo : TodoEntity? = null

    var todosToNotify: ArrayList<TodoEntity> = ArrayList()

    fun onEvent(event: TodoEvent) {
        when(event) {
            is TodoEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todoEntity.id}"))
            }
            is TodoEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoEvent.OnUndoDeleteClick -> {
                deletedTodo?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.insertTodo(it)
                    }
                }
            }
            is TodoEvent.OnDeleteTodoClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deletedTodo = event.todoEntity
                    repository.deleteTodo(event.todoEntity)
                    // after delete, set it to null again
                    deletedTodo = null
                }
            }
            is TodoEvent.OnDoneChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertTodo(
                        event.todoEntity
                            .copy(
                                isDone = event.isDone
                            )
                    )
                }
            }
            is TodoEvent.OnSetTodoToDelete -> {
                viewModelScope.launch {
                    deletedTodo = event.todoEntity
                }
            }
            is TodoEvent.OnSetTodoToDeleteToNull -> {
                viewModelScope.launch {
                    deletedTodo = null
                }
            }
            is TodoEvent.OnCheckDueDatesForAllTodos -> {
                viewModelScope.launch(Dispatchers.IO) {
                    for (item in event.todosToNotifyList){
                        todosToNotify.add(item)
                        Log.i("MYTAG", "items send to View Model ${item.title}")

                    }
                    Log.i("MYTAG ","UPDATED todosToNotify last $todosToNotify")
                }
            }
            is TodoEvent.OnSendNotifyNotificationRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    for (todo in event.todosNotified){
                        repository.insertTodo(
                            todo.copy(
                                isNotified = 1
                            )
                        )
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}

