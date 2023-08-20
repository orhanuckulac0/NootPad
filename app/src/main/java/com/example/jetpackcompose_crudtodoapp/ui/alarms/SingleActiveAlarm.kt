package com.example.jetpackcompose_crudtodoapp.ui.alarms

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Cancel
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
import androidx.core.graphics.toColorInt
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager.cancelAlarm
import com.example.jetpackcompose_crudtodoapp.ui.theme.WhiteBackground
import com.example.jetpackcompose_crudtodoapp.ui.util.AlarmDataStore
import com.vanpra.composematerialdialogs.MaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleActiveAlarm(
    todoEntity: TodoEntity,
    viewModel: AlarmViewModel = hiltViewModel(),
    context: Context,
    timeDialogState: MaterialDialogState,
    pickedTime: MutableState<LocalTime>
    )
{
    ShowTimePicker(
        timeDialogState = timeDialogState,
        context = context,
        todo = todoEntity,
        pickedTime = pickedTime
    )

    val dataStore = AlarmDataStore(LocalContext.current)
    val scope = rememberCoroutineScope()

    Card(
        elevation = 10.dp,
        modifier =
        Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                border = BorderStroke(
                    1.dp,
                    color = Color(todoEntity.priorityColor.toColorInt())
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxSize()
            .background(WhiteBackground)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    text = todoEntity.title,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable {
                        timeDialogState.show()
                    }
                ) {
                    Row {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            text= todoEntity.dueDate,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            text= todoEntity.alarmDate!!,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                IconButton(
                    onClick = {
                        cancelAlarm(context, todoEntity.id!!)
                        viewModel.onEvent(AlarmEvents.OnAlarmCancelled(todoEntity))
                        scope.launch {
                            val uniqueKey = "${todoEntity.id}-${todoEntity.alarmDate}-${todoEntity.dueDate}"
                            val key = stringPreferencesKey(uniqueKey)
                            dataStore.deleteDataByKey(key)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "CancelAlarm",
                        tint = Color.Blue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}