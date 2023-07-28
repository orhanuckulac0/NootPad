package com.example.jetpackcompose_crudtodoapp.ui.alarms

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.ui.theme.BlueColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AlarmViewModel = hiltViewModel(),
){
    val context = LocalContext.current.applicationContext
    val showDialog = remember { mutableStateOf(false) }
    val timeDialogState = rememberMaterialDialogState()
    val pickedTime = remember { mutableStateOf(LocalTime.now()) }

    if (showDialog.value){
        SelectTodoDialog(
            viewModel.todosWithoutAlarmSet.value,
            onDismiss = {
                showDialog.value = false
            },
            context = context,
            timeDialogState,
            pickedTime,
            onTodoClicked = { clickedTodo ->
                viewModel.addedToAlarmTodo = clickedTodo
            }

        )
    }

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else-> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MainBackgroundColor,
                title = { Text(
                    text = "Alarms",
                    color = MainTextColor )
                },
            )
        }, content = {
            it.calculateTopPadding()
            Column(
                modifier = Modifier
                    .background(MainBackgroundColor)
                    .fillMaxSize()) {
                if (viewModel.todosWithAlarmSet.value.isEmpty()){
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.padding(top = 25.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "You don't have any active alarms.",
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }
                    }
                }else{
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        itemsIndexed(viewModel.todosWithAlarmSet.value) { _, todo ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                SingleActiveAlarm(
                                    todoEntity = todo,
                                    context = context
                                )
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    showDialog.value = true
                },
                backgroundColor = BlueColor
            ) {
                Icon(
                    imageVector = Icons.Default.Alarm,
                    contentDescription = "Add Alarm",
                    tint = colorResource(id = R.color.white)
                )
            }
        }
    )
}