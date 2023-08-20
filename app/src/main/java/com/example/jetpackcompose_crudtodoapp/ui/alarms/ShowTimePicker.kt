package com.example.jetpackcompose_crudtodoapp.ui.alarms

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager.cancelAlarm
import com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager.setAlarm
import com.example.jetpackcompose_crudtodoapp.ui.util.AlarmDataStore
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowTimePicker(
    timeDialogState: MaterialDialogState,
    context: Context,
    todo: TodoEntity,
    pickedTime: MutableState<LocalTime>,
    viewModel: AlarmViewModel = hiltViewModel(),
    ){
    val dataStore = AlarmDataStore(LocalContext.current)
    val scope = rememberCoroutineScope()

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(Constants.OK){
                if (todo.isAlarmSet!!){
                    cancelAlarm(context, todo.id!!)
                    setAlarm(context, todo.id, todo.dueDate, pickedTime.value.toString())
                    viewModel.onEvent(AlarmEvents.OnAlarmAdded(viewModel.addedToAlarmTodo!!, pickedTime.value.toString()))
                }else{
                    setAlarm(context, todo.id!!, todo.dueDate, pickedTime.value.toString())
                    viewModel.onEvent(AlarmEvents.OnAlarmAdded(viewModel.addedToAlarmTodo!!, pickedTime.value.toString()))
                }
            }
            negativeButton(Constants.CANCEL){  }
        }
    ) {
        val currentTime = LocalTime.now().plusMinutes(1)
        val maxTime = LocalTime.MAX
        val timeRange = currentTime..maxTime

        timepicker(timeRange = timeRange, initialTime = LocalTime.now()) { time ->
            val pickerTime = LocalTime.parse(time.toString())
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime = pickerTime.format(formatter)
            val localTime = LocalTime.parse(formattedTime, formatter)
            pickedTime.value = localTime
            viewModel.onEvent(AlarmEvents.OnAlarmAdded(viewModel.addedToAlarmTodo!!, pickedTime.value.toString()))
            scope.launch {
                dataStore.saveDataUnique(
                    id = viewModel.addedToAlarmTodo!!.id!!,
                    alarmTime = pickedTime.value.toString(),
                    alarmDate = viewModel.addedToAlarmTodo!!.dueDate
                )
            }
        }
    }
}