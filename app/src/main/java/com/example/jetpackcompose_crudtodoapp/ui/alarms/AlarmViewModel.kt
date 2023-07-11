package com.example.jetpackcompose_crudtodoapp.ui.alarms

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.domain.use_case.AddEditTodoUseCase
import com.example.jetpackcompose_crudtodoapp.domain.use_case.GetTodoWithAlarmSet
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
    private val addEditTodoUseCase: AddEditTodoUseCase,
    private val getTodoWithoutAlarmSet: GetTodoWithoutAlarmSet,
    private val getTodoWithAlarmSet: GetTodoWithAlarmSet
): ViewModel() {

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var _todosWithAlarmSet = mutableStateOf<List<TodoEntity>>(emptyList())
    val todosWithAlarmSet: State<List<TodoEntity>> = _todosWithAlarmSet

    private val _todosWithoutAlarmSet = mutableStateOf<List<TodoEntity>>(emptyList())
    val todosWithoutAlarmSet: State<List<TodoEntity>> = _todosWithoutAlarmSet

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                withAlarmSet()
                withoutAlarmSet()
            }
        }
    }
    suspend fun withAlarmSet(){
        _todosWithAlarmSet.value = getTodoWithAlarmSet.getTodoWithAlarmSet()
    }
    private suspend fun withoutAlarmSet(){
        _todosWithoutAlarmSet.value = getTodoWithoutAlarmSet.getTodoWithoutAlarmSet()
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