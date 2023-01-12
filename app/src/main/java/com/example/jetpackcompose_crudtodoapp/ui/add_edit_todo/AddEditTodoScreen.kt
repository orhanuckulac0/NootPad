package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.util.UiEvent

@Composable
fun AddEditTodoScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message, event.action, SnackbarDuration.Long)
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
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) {
        it.calculateBottomPadding()
        it.calculateTopPadding()
        Column(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)) {
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
                },

                label = { Text("Title") },
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                },
                label = { Text("Description") },
                modifier = modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}