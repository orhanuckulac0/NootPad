package com.example.jetpackcompose_crudtodoapp.ui.alarms

import android.os.Build
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
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

    var addedToAlarmTodo: TodoEntity? = null
    var updatedDueDate: String = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTodos()
        }
    }

    private suspend fun getAllTodos(){
        val todosToUpdate = mutableListOf<TodoEntity>()

        todosToUpdate.forEach { updateTodo(it) }

        withContext(Dispatchers.Main) {
            delay(200)
            withAlarmSet()
            withoutAlarmSet()
        }
    }

    private suspend fun withAlarmSet(){
        _todosWithAlarmSet.value = getTodoWithAlarmSet.getTodoWithAlarmSet()
    }
    private suspend fun withoutAlarmSet(){
        _todosWithoutAlarmSet.value = getTodoWithoutAlarmSet.getTodoWithoutAlarmSet()
        val filteredList = mutableListOf<TodoEntity>()

        todosWithoutAlarmSet.value.forEach {
            filteredList.add(it)
        }
        _todosWithoutAlarmSet.value = filteredList
    }

    private fun updateTodo(todoEntity: TodoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            addEditTodoUseCase.addEditTodo(todoEntity.copy(
                alarmDate = null, isAlarmSet = false)
            )
        }
    }

    fun onEvent(event: AlarmEvents) {
        when (event) {
            is AlarmEvents.OnAlarmAdded -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val updatedTodo = event.todo.copy(
                        alarmDate = event.alarmTime,
                        isAlarmSet = true,
                        dueDate = updatedDueDate.ifEmpty { event.todo.dueDate }
                    )

                    addEditTodoUseCase.addEditTodo(updatedTodo)

                    _todosWithoutAlarmSet.value = getTodoWithoutAlarmSet.getTodoWithoutAlarmSet()
                    _todosWithAlarmSet.value = getTodoWithAlarmSet.getTodoWithAlarmSet()
                }
            }
            is AlarmEvents.OnAlarmCancelled -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addEditTodoUseCase.addEditTodo(
                        event.todo.copy(
                            alarmDate = null,
                            isAlarmSet = null
                        )
                    )
                    _todosWithAlarmSet.value = todosWithAlarmSet.value.filter { todoInList -> todoInList.id != event.todo.id }
                    val updated = todosWithoutAlarmSet.value
                        .filterNot { it.id == event.todo.id }
                        .toMutableList()
                        .apply { add(event.todo) }
                        .sortedBy { it.id }

                    _todosWithoutAlarmSet.value = updated
                }
            }
            is AlarmEvents.OnTodoDueDateChanged -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        addEditTodoUseCase.addEditTodo(
                            event.todo.copy(
                                dueDate = event.newDueDate
                            )
                        )

                    } catch (_: Exception) { }
                }
            }
        }
    }
}