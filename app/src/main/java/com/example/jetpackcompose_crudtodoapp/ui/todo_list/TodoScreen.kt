package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.ui.theme.BlueColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.example.jetpackcompose_crudtodoapp.ui.util.CustomDialog
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun TodoScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel(),
    shouldShowDialog: MutableState<Boolean>
) {
    LaunchedEffect(key1 = true) {
        viewModel.getTodos(Constants.ALL)

        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }


    val todos = viewModel.todos.value
    val loading = viewModel.loading.value
    val selectedChipIndex = remember {
        mutableIntStateOf(0)
    }

    if (shouldShowDialog.value){
        CustomDialog(
            setShowDialog =  {
            shouldShowDialog.value = it
            },
            deletedFromScreen = "TodoScreen"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MainBackgroundColor,
                title = { Text(
                    text = "NootPad",
                    color = MainTextColor )
                        },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(TodoEvent.OnAlarmClicked)
                    }) {
                        Icon(imageVector = Icons.Default.Alarm, contentDescription = "Alarms", tint = Color.Blue)
                    }
                }
            )
        },
        content = {
            it.calculateBottomPadding()

            Column(
                modifier = Modifier
                    .background(MainBackgroundColor)
                    .fillMaxSize()) {
                Row(modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 0.dp)) {
                    CategorySection(selectedChipIndex = selectedChipIndex)
                }
                Row {
                    Column {
                        Row(modifier = Modifier.padding(15.dp,5.dp,0.dp,15.dp)) {
                            Text(
                                text = Constants.TASKS,
                                color= Color.Gray,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }
                        if (loading) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(top = 100.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.drawBehind {
                                        drawCircle(
                                            Color.White,
                                            radius = size.width / 2 - 5.dp.toPx() / 2,
                                            style = Stroke(5.dp.toPx())
                                        )
                                    },
                                    color = Color.LightGray,
                                    strokeWidth = 5.dp
                                )
                            }
                        } else {
                            if (todos.isEmpty()) {
                                EmptyTodoScreen(modifier = Modifier)
                            } else {
                                TodosList(
                                    todos = todos,
                                    viewModel = viewModel,
                                    shouldShowDialog = shouldShowDialog
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
                    viewModel.onEvent(TodoEvent.OnAddTodoClick)
                },
                backgroundColor = BlueColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.ADD_NEW_TODO,
                    tint= colorResource(id = R.color.white)
                )
            }
        }
    )
}
