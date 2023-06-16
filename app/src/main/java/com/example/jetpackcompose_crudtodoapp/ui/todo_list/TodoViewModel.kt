package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.use_case.AddEditTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.DeleteTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetAllTodosUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodosByCategory
import com.example.jetpackcompose_crudtodoapp.ui.navigation.Routes
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val addEditTodoUseCase: AddEditTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getTodosByCategory: GetTodosByCategory
): ViewModel() {

    val todos: MutableState<List<TodoEntity>> = mutableStateOf(listOf())
    val loading: MutableState<Boolean> = mutableStateOf(true)

    var selectedCategory = mutableStateOf(Constants.ALL)

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var deletedTodo : TodoEntity? = null

    init {
        viewModelScope.launch {
            todos.value = getAllTodosUseCase.getAllTodos()
            loading.value = false
        }
    }

    fun onEvent(event: TodoEvent) {
        when(event) {
            is TodoEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.TODO_INFO + "?todoId=${event.todoEntity.id}"))
            }
            is TodoEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoEvent.OnUndoDeleteClick -> {
                deletedTodo?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        addEditTodoUseCase.addEditTodo(it)
                    }
                }
            }
            is TodoEvent.OnDeleteTodoClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deletedTodo = event.todoEntity
                    deleteTodoUseCase.deleteTodo(event.todoEntity)
                    getTodos(selectedCategory.value)
                }
            }
            is TodoEvent.OnDoneChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addEditTodoUseCase.addEditTodo(
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
            todos.value = getAllTodosUseCase.getAllTodos()
        }else{
            todos.value = getTodosByCategory.getTodosByCategory(category)
        }
    }
}

