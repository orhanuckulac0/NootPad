package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.use_case.AddEditTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodoByIDUseCase
import com.example.jetpackcompose_crudtodoapp.ui.navigation.Routes
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent
import com.example.jetpackcompose_crudtodoapp.ui.util.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val addTodoUseCase: AddEditTodoUseCase,
    private val getTodoByIDUseCase: GetTodoByIDUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var todoEntity by mutableStateOf<TodoEntity?>(null)
        private set

    private var todoId by mutableIntStateOf(0)

    var topAppBarText by mutableStateOf("")

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var dueDate by mutableStateOf("")
        private set

    var alarmDate by mutableStateOf<String?>(null)
        private set

    var priorityColor by mutableStateOf("")
        private set

    var category by mutableStateOf("")
        private set

    var isAlarmSet by mutableStateOf<Boolean?>(null)
        private set

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1){
            viewModelScope.launch {
                getTodoByIDUseCase.getTodoByID(todoId)?.let {
                    title = it.title
                    description = it.description
                    dueDate = it.dueDate
                    priorityColor = it.priorityColor
                    category = it.category
                    alarmDate = it.alarmDate
                    isAlarmSet = it.isAlarmSet
                    this@AddEditTodoViewModel.todoEntity = it
                }
            }
            topAppBarText = Constants.EDIT_TODO
        }else{
            topAppBarText = Constants.ADD_NEW_TODO

        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when(event) {
            is AddEditTodoEvent.OnDatePickerClick -> {
                sendUiEvent(UiEvent.ShowDatePicker)
            }
            is AddEditTodoEvent.OnTimePickerClicked -> {
                sendUiEvent(UiEvent.ShowTimePicker)
            }
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnDueDateChange -> {
                dueDate = event.dueDate
            }
            is AddEditTodoEvent.OnTimeDateChange -> {
                alarmDate = event.timeDate
            }
            is AddEditTodoEvent.OnPriorityColorChange -> {
                priorityColor = event.priorityColor
            }
            is AddEditTodoEvent.OnCategoryChange -> {
                category = event.category
            }
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (title.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackbar(Constants.EMPTY_TITLE))
                        return@launch
                    }
                    if (dueDate.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackbar(Constants.EMPTY_DUE_DATE))
                        return@launch
                    }
                    if (priorityColor.isBlank()){
                        priorityColor = toHexString(Color(0xFF808080))
                    }
                    if (category.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackbar(Constants.EMPTY_CATEGORY))
                        return@launch
                    }

                    addEditTodo()
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

    private suspend fun addEditTodo() {
        addTodoUseCase.addEditTodo(
            TodoEntity(
                id = todoEntity?.id,
                title = title,
                description = description,
                isDone = todoEntity?.isDone ?: false,
                dueDate = dueDate,
                alarmDate = alarmDate,
                isAlarmSet = todoEntity?.isAlarmSet ?: false,
                priorityColor = priorityColor,
                category = category
            )
        )
    }
}

