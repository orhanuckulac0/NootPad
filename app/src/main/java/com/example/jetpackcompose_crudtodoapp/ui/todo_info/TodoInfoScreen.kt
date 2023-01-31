package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.util.UiEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoInfoScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoInfoViewModel = hiltViewModel(),
) {

    val scrollableDescription = rememberScrollState()

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message, event.action, SnackbarDuration.Short)
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else-> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    viewModel.onEvent(TodoInfoEvent.OnEditTodoClick)
                },
                backgroundColor = colorResource(id = R.color.purple_700)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Todo",
                    tint=colorResource(id = R.color.white)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                backgroundColor = colorResource(id = R.color.purple_700),
                cutoutShape = CircleShape) {
            }
        }
    ) {
        it.calculateBottomPadding()
        it.calculateTopPadding()
        Column(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Row() {
                Text(text = "Title")
            }
            Spacer(modifier = modifier.height(8.dp))
            Row() {
                Text(text = viewModel.title)
            }
            Spacer(modifier = modifier.height(8.dp))
            Column() {
                Row() {
                    Text(text = "Due Date:")
                    Text(text = viewModel.dueDate)
                }
            }
            Spacer(modifier = modifier.height(8.dp))
            Text(text = "Description:")
            Row(modifier = Modifier
                .verticalScroll(scrollableDescription)
                .fillMaxWidth()
            ) {
                Text(text = viewModel.description)
            }
            Spacer(modifier = modifier.height(8.dp))

        }
    }
}
