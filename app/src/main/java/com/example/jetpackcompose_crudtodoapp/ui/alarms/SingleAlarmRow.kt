package com.example.jetpackcompose_crudtodoapp.ui.alarms

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager.setAlarm
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.WhiteBackground
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
fun SingleAlarmRow(
    todo: TodoEntity,
    timeDialogState: MaterialDialogState,
    pickedTime: MutableState<LocalTime>,
    viewModel: AlarmViewModel = hiltViewModel(),
    context: Context,
    onClick: () -> Unit
)

{
    val dataStore = AlarmDataStore(LocalContext.current)
    val scope = rememberCoroutineScope()

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(Constants.OK){
                setAlarm(context, todo.id!!, todo.dueDate, pickedTime.value.toString())
                viewModel.onEvent(AlarmEvents.OnAlarmAdded(viewModel.addedToAlarmTodo!!, pickedTime.value.toString()))
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

    Card(
        elevation = 5.dp,
        modifier =
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                border = BorderStroke(
                    1.dp,
                    color = Color(todo.priorityColor.toColorInt())
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxSize()
            .background(WhiteBackground)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 5.dp, top = 5.dp)
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = todo.title,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MainTextColor
                )
                Spacer(modifier = Modifier.padding(bottom = 2.dp))
                Text(text = todo.dueDate, color = Color.Gray, fontSize = 14.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = {
                        onClick()
                        timeDialogState.show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "AddAlarmBox",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}