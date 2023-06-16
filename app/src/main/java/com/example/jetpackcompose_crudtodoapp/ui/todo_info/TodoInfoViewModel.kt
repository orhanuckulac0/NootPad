package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.repository.TodoRepository
import com.example.jetpackcompose_crudtodoapp.domain.use_case.DeleteTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodoByIDUseCase
import com.example.jetpackcompose_crudtodoapp.ui.navigation.Routes
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoInfoViewModel @Inject constructor(
    private val getTodoByIDUseCase: GetTodoByIDUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var todoEntity by mutableStateOf<TodoEntity?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var dueDate by mutableStateOf("")
        private set

    var priorityColor by mutableStateOf("")
        private set

    var category by mutableStateOf("")
        private set

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        if (todoId != -1 && todoId != null){
            viewModelScope.launch {
                getTodoByIDUseCase.getTodoByID(todoId)?.let {
                    title = it.title
                    description = it.description
                    dueDate = it.dueDate
                    priorityColor = it.priorityColor
                    category = it.category
                    this@TodoInfoViewModel.todoEntity = it
                }
            }
        }
    }

    fun onEvent(event: TodoInfoEvent){
        when(event) {
            is TodoInfoEvent.OnEditTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${todoEntity!!.id}"))
            }
            is TodoInfoEvent.OnDeleteTodoClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteTodoUseCase.deleteTodo(todoEntity!!)
                }
                sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvent.emit(event)
        }
    }
}