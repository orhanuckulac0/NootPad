package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    viewModel: TodoViewModel = hiltViewModel()
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

    val shouldShowDialog = remember { mutableStateOf(false) }

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
                ChipSection()
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
                    if (todos.isEmpty()){
                        EmptyTodoScreen(modifier = Modifier)
                    }else{
                        TodosListComposable(
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
