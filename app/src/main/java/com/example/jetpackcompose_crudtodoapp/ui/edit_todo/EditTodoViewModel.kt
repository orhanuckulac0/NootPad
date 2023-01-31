package com.example.jetpackcompose_crudtodoapp.ui.edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.data.TodoEntity
import com.example.jetpackcompose_crudtodoapp.data.TodoRepository
import com.example.jetpackcompose_crudtodoapp.navigation.Routes
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import com.example.jetpackcompose_crudtodoapp.util.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var todoEntity by mutableStateOf<TodoEntity?>(null)
        private set // can only change the value of this within the view model

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var dueDate by mutableStateOf("")
        private set

    var priorityColor by mutableStateOf("")
        private set

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        if (todoId != -1 && todoId != null){
            // remove Dispatchers.IO because it executes before recomposition and causes error
            viewModelScope.launch {
                todoRepository.getTodoByID(todoId)?.let {
                    title = it.title
                    description = it.description
                    dueDate = it.dueDate
                    priorityColor = it.priorityColor
                    // assign this todoEntity to ViewModel's todoEntity state
                    this@EditTodoViewModel.todoEntity = it
                }
            }
        }
    }

    fun onEvent(event: EditTodoEvent) {
        when(event) {
            is EditTodoEvent.OnTitleChange -> {
                title = event.title
            }
            is EditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is EditTodoEvent.OnDueDateChange -> {
                dueDate = event.dueDate
            }
            is EditTodoEvent.OnPriorityColorChange -> {
                priorityColor = event.priorityColor
            }
            is EditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (title.isBlank()){
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                            "The title can not be blank.",
                        ))
                        return@launch
                    }
                    if (dueDate.isBlank()){
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                            "The due date can not be blank.",
                        ))
                        return@launch
                    }
                    if (priorityColor.isBlank()){
                        priorityColor = toHexString(Color(0xFF808080))
                    }
                    todoRepository.insertTodo(
                        TodoEntity(
                            id = todoEntity?.id,
                            title = title,
                            description = description,
                            isDone = todoEntity?.isDone ?: false,  // if isDone is null, it will be false
                            dueDate = dueDate,
                            priorityColor = priorityColor
                        )
                    )
                    sendUiEvent(UiEvent.Navigate(Routes.TODO_INFO + "?todoId=${todoEntity?.id}"))
                }
            }
            is EditTodoEvent.OnDatePickerClick -> {
                sendUiEvent(UiEvent.ShowDatePicker)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvent.emit(event)
        }
    }

}