package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.data.TodoEntity
import com.example.jetpackcompose_crudtodoapp.data.TodoRepository
import com.example.jetpackcompose_crudtodoapp.navigation.Routes
import com.example.jetpackcompose_crudtodoapp.util.Constants
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    val todos: MutableState<List<TodoEntity>> = mutableStateOf(listOf())
    val loading: MutableState<Boolean> = mutableStateOf(true)

    var selectedCategory = mutableStateOf(Constants.ALL)

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var deletedTodo : TodoEntity? = null

    init {
        viewModelScope.launch {
            todos.value = repository.getAllTodos()
            loading.value = false
        }
    }

    fun onEvent(event: TodoEvent) {
        when(event) {
            is TodoEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.TODO_INFO + "?todoId=${event.todoEntity.id}"))
            }
            is TodoEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_TODO))
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
                    getTodos(selectedCategory.value)
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
                    getTodos(selectedCategory.value)
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
            is TodoEvent.OnCategoryClicked -> {
                viewModelScope.launch {
                    getTodos(event.category)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    suspend fun getTodos(category: String){
        if (category == Constants.ALL){
            todos.value = repository.getAllTodos()
        }else{
            todos.value = repository.getTodosByCategory(category)
        }
    }
}

