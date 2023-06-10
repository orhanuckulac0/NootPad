package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.util.Constants
import com.example.jetpackcompose_crudtodoapp.util.CustomDialog
import com.example.jetpackcompose_crudtodoapp.util.UiEvent


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
        mutableStateOf(0)
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
                title = { Text(text = "${viewModel.selectedCategory.value} Todos" ) },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(TodoEvent.OnAddTodoClick)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add New Todo",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        it.calculateBottomPadding()

        Column(
            modifier = Modifier
                .background(MainBackgroundColor)
                .fillMaxSize()) {
            Row(modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 0.dp)) {
                ChipSection(selectedChipIndex = selectedChipIndex)
            }
            Row {
                Column {
                    Row(modifier = Modifier.padding(15.dp,5.dp,0.dp,15.dp)) {
                        Text(
                            text = Constants.TASKS,
                            color= Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                    if (loading) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top=100.dp),
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
    }
}
