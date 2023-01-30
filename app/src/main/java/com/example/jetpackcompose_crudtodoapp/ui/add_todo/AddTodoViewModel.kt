package com.example.jetpackcompose_crudtodoapp.ui.add_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.data.TodoEntity
import com.example.jetpackcompose_crudtodoapp.data.TodoRepository
import com.example.jetpackcompose_crudtodoapp.util.Routes
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import com.example.jetpackcompose_crudtodoapp.util.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
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


    fun onEvent(event: AddTodoEvent) {
        when(event) {
            is AddTodoEvent.OnDatePickerClick -> {
                sendUiEvent(UiEvent.ShowDatePicker)
            }
            is AddTodoEvent.OnTitleChange -> {
                title = event.title
            }
            is AddTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddTodoEvent.OnDueDateChange -> {
                dueDate = event.dueDate
            }
            is AddTodoEvent.OnPriorityColorChange -> {
                priorityColor = event.priorityColor
            }
            is AddTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (title.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackbar(
                            "The title can not be blank.",
                        ))
                        return@launch
                    }
                    if (dueDate.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackbar(
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
                    // go back to main screen
                    sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvent.emit(event)
        }
    }
}