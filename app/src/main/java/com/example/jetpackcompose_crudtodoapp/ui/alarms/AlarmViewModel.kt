package com.example.jetpackcompose_crudtodoapp.ui.alarms

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.use_case.AddEditTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetAllTodosUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodoWithoutAlarmSet
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val addEditTodoUseCase: AddEditTodoUseCase,
    private val getTodoWithoutAlarmSet: GetTodoWithoutAlarmSet
): ViewModel() {

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var allTodos = emptyList<TodoEntity>()

    private val _todosWithoutAlarmSet = mutableStateOf<List<TodoEntity>>(emptyList()) // MutableState holds the mutable list
    val todosWithoutAlarmSet: State<List<TodoEntity>> = _todosWithoutAlarmSet // Expose the immutable State to observe in the Composable

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val todos = getTodoWithoutAlarmSet.getTodoWithoutAlarmSet()
            allTodos = getAllTodosUseCase.getAllTodos()

            withContext(Dispatchers.Main) {
                _todosWithoutAlarmSet.value = todos
            }
        }
    }

    fun onEvent(event: AlarmEvents){
        when(event){
            is AlarmEvents.OnAlarmAdded ->{
                viewModelScope.launch(Dispatchers.IO) {
                    addEditTodoUseCase.addEditTodo(
                        event.todo.copy(
                            alarmDate = event.alarmTime, isAlarmSet = true
                        )
                    )
                    _todosWithoutAlarmSet.value = getTodoWithoutAlarmSet.getTodoWithoutAlarmSet()
                    _todosWithoutAlarmSet.value = todosWithoutAlarmSet.value.filter { todoInList -> todoInList.id != event.todo.id }
                }
            }
        }
    }

}