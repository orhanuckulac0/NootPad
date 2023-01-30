package com.example.jetpackcompose_crudtodoapp.ui.add_todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.util.Routes
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import com.example.jetpackcompose_crudtodoapp.util.toHexString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.color.colorChooser
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTodoScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: (destinationId: String) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AddTodoViewModel = hiltViewModel(),
) {

    val scrollableDescription = rememberScrollState()

    // DatePicker related
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("d MMM yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok", onClick = {
                viewModel.onEvent(AddTodoEvent.OnDueDateChange(formattedDate))
            })
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = Color.Cyan,
                dateActiveBackgroundColor = Color.Cyan,
            ),
        ) {
            pickedDate = it
        }
    }

    // Color Picker Related
    val colorPickerDialogState = rememberMaterialDialogState()
    var pickedColor by remember {
        mutableStateOf("")
    }
    val listOfColors = listOf(
        Color(0xFF808080),
        Color(0xFF000000),
        Color(0xFFFF0000),
        Color(0xFF0000FF),
        Color(0xFFFFFF00),
        Color(0xFF00FF00),
        Color(0xFFFFA500),
        Color(0xFF30D5C8),
        Color(0xFFA020F0),
        Color(0xFF964B00),
    )

    MaterialDialog(
        dialogState = colorPickerDialogState,
        buttons = {
            positiveButton(text = "Select", onClick = {
                viewModel.onEvent(AddTodoEvent.OnPriorityColorChange(pickedColor))
            })
            negativeButton(text = "Cancel", onClick = {
//                viewModel.onEvent(AddEditTodoEvent.OnPriorityColorChange(toHexString(listOfColors[0])))
            })
        }
    ) {
        colorChooser(
            colors = listOfColors,
            onColorSelected = {
                pickedColor = toHexString(it)
            }
        )
    }

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.PopBackStack -> {
                    onPopBackStack(Routes.TODO_LIST)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message, event.action, SnackbarDuration.Short)
                }
                is UiEvent.ShowDatePicker -> {
                    dateDialogState.show()
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
                viewModel.onEvent(AddTodoEvent.OnSaveTodoClick)
                },
                backgroundColor = colorResource(id = R.color.purple_700)
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save", tint=colorResource(id = R.color.white))
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(backgroundColor = colorResource(id = R.color.purple_700), cutoutShape = CircleShape) {

            }
        }
    ) {
        it.calculateBottomPadding()
        it.calculateTopPadding()
        Column(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Column {
                Row {
                    TextField(
                        value = viewModel.title,
                        onValueChange = {
                            viewModel.onEvent(AddTodoEvent.OnTitleChange(it))
                        },
                        label = { Text("Title") },
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .weight(0.9f),
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                    )
                }
            }
            Spacer(modifier = modifier.height(8.dp))
            
            Column(
                modifier = Modifier
                    .verticalScroll(scrollableDescription)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = viewModel.description,
                    onValueChange = {
                        viewModel.onEvent(AddTodoEvent.OnDescriptionChange(it))
                    },
                    label = { Text("Description") },
                    modifier = modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    singleLine = false,
                    maxLines = 10
                )
            }

            Spacer(modifier = modifier.height(8.dp))

            Column {
                Row {
                    OutlinedButton(onClick = {
                        viewModel.onEvent(AddTodoEvent.OnDatePickerClick)
                    }, modifier = modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                        Text(
                            text = "Select Due Date",
                            color = Color.Black,
                        )
                    }

                    if (viewModel.dueDate != ""){
                        Text(
                            modifier = modifier.padding(30.dp, 10.dp),
                            text = viewModel.dueDate
                        )
                    }else{
                        Text(
                            modifier = modifier.padding(30.dp, 10.dp),
                            text = "Not Selected Yet"
                        )
                    }
                }
            }
            Column {
                Row {
                    OutlinedButton(onClick = {
                        colorPickerDialogState.show()
                    }, modifier = modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                        Text(
                            text = "Select Priority Color",
                            color = Color.Black
                        )
                    }

                    if (viewModel.priorityColor == ""){
                        Button(
                            onClick = { },
                            modifier = modifier
                                .background(Color.White),
                            colors= ButtonDefaults.buttonColors(backgroundColor = (Color.Gray))
                        ) {

                        }
                    }else{
                        Button(
                            onClick = { },
                            modifier = modifier
                                .background((Color.White)),
                            colors= ButtonDefaults.buttonColors(backgroundColor = (Color(viewModel.priorityColor.toColorInt())))

                        ) {

                        }
                    }
                }
            }
        }
    }
}