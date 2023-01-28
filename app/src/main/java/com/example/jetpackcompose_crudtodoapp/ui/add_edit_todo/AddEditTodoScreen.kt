package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import com.example.jetpackcompose_crudtodoapp.util.toHexString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.color.colorChooser
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditTodoScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel(),
) {

    // DatePicker related
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("d-MMM-yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok", onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnDueDateChange(formattedDate))
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
        Color(0xFFEF9A9A),
        Color(0xFFF48FB1),
        Color(0xFF80CBC4),
        Color(0xFFA5D6A7),
        Color(0xFFFFCC80),
        Color(0xFFFFAB91),
        Color(0xFF81D4FA),
        Color(0xFFCE93D8),
        Color(0xFF000000),
        Color(0xFFFF0000),
        Color(0xFF0000FF),
        Color(0xFFFFFF00),
        Color(0xFF00FF00),
    )

    MaterialDialog(
        dialogState = colorPickerDialogState,
        buttons = {
            positiveButton(text = "Select", onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnPriorityColorChange(pickedColor))
            })
            negativeButton(text = "Cancel", onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnPriorityColorChange(toHexString(listOfColors[0])))
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
                    onPopBackStack()
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message, event.action, SnackbarDuration.Short)
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
                        text = viewModel.dueDate
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
            Column {
                Row {
                    Text(
                        modifier = modifier.padding(0.dp, 25.dp, 10.dp, 0.dp),
                        text = "Priority Color: ",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    if (viewModel.priorityColor == ""){
                        Button(
                            onClick = {
                                colorPickerDialogState.show()
                                      },
                            modifier = modifier
                                .background(Color.White)
                                .clip(RoundedCornerShape(20.dp))
                                .requiredSize(70.dp),
                            shape = CircleShape,
                            colors= ButtonDefaults.buttonColors(backgroundColor = (Color.Gray))
                        ) {

                        }
                    }else{
                        Button(
                            onClick = { colorPickerDialogState.show() },
                            modifier = modifier
                                .background((Color.White))
                                .clip(RoundedCornerShape(20.dp))
                                .requiredSize(70.dp),
                            shape = CircleShape,
                            colors= ButtonDefaults.buttonColors(backgroundColor = (Color(viewModel.priorityColor.toColorInt())))

                        ) {

                        }
                    }
                }
            }
        }
    }
}