package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.ui.theme.WhiteBackground
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodosList(
    todos: List<TodoEntity>,
    viewModel: TodoViewModel,
    shouldShowDialog: MutableState<Boolean>
){

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
                    shouldShowDialog.value = true
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
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                border = BorderStroke(1.dp,
                                color = Color(todo.priorityColor.toColorInt())),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()

                            .background(WhiteBackground)

                    ) {
                        TodoItem(
                            todo = todo,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(TodoEvent.OnTodoClick(todo))
                                }
                                .background(WhiteBackground)
                                .padding(16.dp)
                        )
                    }
                }
            )
        }
    }
}