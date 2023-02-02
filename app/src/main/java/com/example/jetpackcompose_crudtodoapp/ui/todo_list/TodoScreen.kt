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
import com.example.jetpackcompose_crudtodoapp.ui.theme.BackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.TodoBackground
import com.example.jetpackcompose_crudtodoapp.util.CustomDialog
import com.example.jetpackcompose_crudtodoapp.util.UiEvent


@ExperimentalMaterialApi
@Composable
fun TodoScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {

    val categories = listOf("All","Home", "School", "Work", "Sports", "Fun", "Friends", "Other")

    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    var shouldShowDialog by remember { mutableStateOf(false) }

    if (shouldShowDialog){
        CustomDialog(
            setShowDialog =  {
            shouldShowDialog = it
            }
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "All Todos") },
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
                .background(BackgroundColor)
                .fillMaxSize()) {
            Row(modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 0.dp)) {
                ChipSection(chips = categories)
            }
            Row() {
                Column() {
                    Row(modifier = Modifier.padding(15.dp,5.dp,0.dp,15.dp)) {
                        Text(text = "TASKS", color= Color.LightGray, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .padding(15.dp, 0.dp, 15.dp, 0.dp)
                    ) {

                        itemsIndexed(todos.value) { index, todo ->
                            // notSwiped and dismissState should be inside lazy column
                            // otherwise unexpected errors can happen
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
                                    // assign current todoEntity to viewModel's deletedTodo var and make it public
                                    viewModel.onEvent(TodoEvent.OnSetTodoToDelete(todo))
                                    dismissState.reset() // reset dismiss state to default
                                    shouldShowDialog = true
                                    dismissState.reset() // again, just to prevent any bugs
                                }
                            } else { // prevent UI bug on left to right swipe
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
                                            DismissValue.Default -> BackgroundColor
                                            DismissValue.DismissedToEnd -> BackgroundColor
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
                                            .background(TodoBackground)
                                    ) {
                                        TodoItem(
                                            todo = todo,
                                            onEvent = viewModel::onEvent,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    viewModel.onEvent(TodoEvent.OnTodoClick(todo))
                                                }
                                                .background(TodoBackground)
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
