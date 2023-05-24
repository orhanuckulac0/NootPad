package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.DarkBlue
import com.example.jetpackcompose_crudtodoapp.util.Constants
import com.example.jetpackcompose_crudtodoapp.util.CustomDialog
import com.example.jetpackcompose_crudtodoapp.util.UiEvent


@ExperimentalMaterialApi
@Composable
fun TodoScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todos = viewModel.todos.value

    var shouldShowDialog by remember { mutableStateOf(false) }

    if (shouldShowDialog){
        CustomDialog(
            setShowDialog =  {
            shouldShowDialog = it
            },
            deletedFromScreen = "TodoScreen"
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
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
                ChipSection()
            }
            Row {
                Column {
                    Row(modifier = Modifier.padding(15.dp,5.dp,0.dp,15.dp)) {
                        Text(text = Constants.TASKS, color= Color.LightGray, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .padding(15.dp, 0.dp, 15.dp, 0.dp)
                    ) {

                        itemsIndexed(todos) { _, todo ->
                            var notSwiped by remember { mutableStateOf(false) }
                            val dismissState = rememberDismissState(
                                confirmStateChange = { dismiss ->
                                    if (dismiss == DismissValue.DismissedToEnd) notSwiped =
                                        !notSwiped
                                    dismiss != DismissValue.DismissedToEnd
                                }
                            )
                            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                                LaunchedEffect(key1 = true) {
                                    viewModel.onEvent(TodoEvent.OnSetTodoToDelete(todo))
                                    dismissState.reset()
                                    shouldShowDialog = true
                                    dismissState.reset()
                                }
                            } else {
                                LaunchedEffect(key1 = true) {
                                    dismissState.reset()
                                }
                            }

                            SwipeToDismiss(
                                state = dismissState,
                                modifier = Modifier.padding(vertical = 4.dp),
                                directions = setOf(DismissDirection.EndToStart),
                                background = {
                                    val color by animateColorAsState(
                                        when (dismissState.targetValue) {
                                            DismissValue.Default -> MainBackgroundColor
                                            DismissValue.DismissedToEnd -> MainBackgroundColor
                                            DismissValue.DismissedToStart -> Color.Red
                                        }
                                    )
                                    val alignment = Alignment.CenterEnd
                                    val icon = Icons.Default.Delete

                                    val scale by animateFloatAsState(
                                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                                    )

                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(horizontal = 20.dp),
                                        contentAlignment = alignment
                                    ) {
                                        Icon(
                                            icon,
                                            contentDescription = "Localized description",
                                            modifier = Modifier.scale(scale)
                                        )
                                    }
                                },
                                dismissContent = {
                                    Card(
                                        elevation = animateDpAsState(
                                            if (dismissState.dismissDirection != null) 4.dp else 50.dp
                                        ).value,
                                        modifier =
                                        Modifier
                                            .clip(RoundedCornerShape(30.dp))
                                            .fillMaxSize()
                                            .background(DarkBlue)
                                    ) {
                                        TodoItem(
                                            todo = todo,
                                            onEvent = viewModel::onEvent,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    viewModel.onEvent(TodoEvent.OnTodoClick(todo))
                                                }
                                                .background(DarkBlue)
                                                .padding(16.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
