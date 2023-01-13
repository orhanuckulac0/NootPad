package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditTodoScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = Color.Cyan,
                dateActiveBackgroundColor = Color.Cyan,
            )
        ) {
            pickedDate = it
            Log.i("MYTAG", formattedDate)
        }
    }

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
                is UiEvent.ShowDatePicker -> {
                    dateDialogState.show()
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
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(backgroundColor = Color.Cyan, cutoutShape = CircleShape) {

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

            Spacer(modifier = modifier.height(8.dp))

            Column {
               Row {
                   Text(
                       modifier = modifier.padding(0.dp, 10.dp, 10.dp, 0.dp),
                       text = "Due Date: ",
                       color = Color.Black,
                       fontWeight = FontWeight.Bold
                   )

                   Text(
                       modifier = modifier.padding(0.dp, 10.dp),
                       text = formattedDate
                   )

                   IconButton(onClick = {
                       viewModel.onEvent(AddEditTodoEvent.OnDatePickerClick)
                   }) {
                       Icon(
                           imageVector = Icons.Default.EditCalendar, contentDescription = "DatePickerIcon", tint = Color.Black
                       )
                   }
               } 
            }
        }
    }
}