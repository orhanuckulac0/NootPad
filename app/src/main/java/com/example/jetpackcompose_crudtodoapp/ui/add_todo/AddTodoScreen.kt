package com.example.jetpackcompose_crudtodoapp.ui.add_todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.ui.edit_todo.EditTodoEvent
import com.example.jetpackcompose_crudtodoapp.ui.theme.DarkBlue
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
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
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: AddTodoViewModel = hiltViewModel(),
) {
    // input related
    val titleMaxCharLength = 70
    val descriptionMaxCharLength = 500

    // Category Dropdown Related
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    val mCategories = listOf("Home", "School", "Work", "Sports", "Fun", "Friends", "Other")
    var mSelectedCategory by remember { mutableStateOf("") }
    var mDropdownSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


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
                headerBackgroundColor = MaterialTheme.colors.primary,
                dateActiveBackgroundColor = MaterialTheme.colors.primary,
            ),
            allowedDateValidator = {
                val yesterdayDate = LocalDate.now().minusDays(1)
                it.isAfter(yesterdayDate)
            }
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
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Todo") },
                actions = {
                    IconButton(onClick = {
                        onPopBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate Back",
                        )
                    }
                }
            )
        }
    ){
        it.calculateBottomPadding()
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
                    backgroundColor = DarkBlue
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save", tint=colorResource(id = R.color.white))
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(backgroundColor = DarkBlue, cutoutShape = CircleShape) {

                }
            }
        ) {
            it.calculateBottomPadding()
            it.calculateTopPadding()
            Column(modifier = modifier
                .fillMaxSize()
                .background(MainBackgroundColor)
                .padding(16.dp)) {

                Column(modifier = modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Row() {
                        Text(
                            text = "Todo Title",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.height(8.dp))

                    Row() {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                        ) {
                            TextField(
                                value = viewModel.title,
                                onValueChange = {
                                    if (it.length <= titleMaxCharLength){
                                        viewModel.onEvent(AddTodoEvent.OnTitleChange(it))
                                    }
                                },
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(DarkBlue),
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                            )
                            Text(
                                text = "${viewModel.title.length} / $titleMaxCharLength",
                                textAlign = TextAlign.End,
                                color = Color.White,
                                style = MaterialTheme.typography.caption, //use the caption text style
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.height(8.dp))

                Column(modifier = modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Row() {
                        Text(
                            text = "Todo Description",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.height(8.dp))

                    Row() {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TextField(
                                value = viewModel.description,
                                onValueChange = {
                                    if (it.length <= descriptionMaxCharLength){
                                        viewModel.onEvent(AddTodoEvent.OnDescriptionChange(it))
                                    }
                                },
                                label = { Text("Description") },
                                modifier = modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.6F)
                                    .clip(RoundedCornerShape(5.dp))
                                    .verticalScroll(scrollableDescription),
                                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                                singleLine = false,
                                maxLines = 15
                            )
                            Text(
                                text = "${viewModel.description.length} / $descriptionMaxCharLength",
                                textAlign = TextAlign.End,
                                color = Color.White,
                                style = MaterialTheme.typography.caption, //use the caption text style
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
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
                                text = viewModel.dueDate,
                                color = Color.White

                            )
                        }else{
                            Text(
                                modifier = modifier.padding(30.dp, 10.dp),
                                text = "Not Selected Yet",
                                color = Color.White
                            )
                        }
                    }
                }
                Column {
                    Row {
                        OutlinedButton(onClick = {
                            mExpanded = !mExpanded
                        }, modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                mDropdownSize = coordinates.size.toSize()
                            }
                            .clickable { mExpanded = !mExpanded }
                        ) {
                            Text(
                                "Select Category",
                                color = Color.Black,
                            )
                            Icon(
                                imageVector = icon,
                                contentDescription = "dropdownIcon",
                                Modifier.clickable { mExpanded = !mExpanded }
                            )
                        }

                        // Create a drop-down menu with list of cities,
                        // when clicked, set the Text Field text as the city selected
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current){mDropdownSize.width.toDp()})
                        ) {
                            mCategories.forEach { category ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(AddTodoEvent.OnCategoryChange(category))
                                    mExpanded = false
                                }) {
                                    Text(text = category)
                                }
                            }
                        }

                        if (viewModel.category == ""){
                            Text(
                                modifier = Modifier.padding(16.dp, 10.dp),
                                text = "Not Selected Yet",
                                color = Color.White

                            )
                        }
                        else{
                            Text(
                                modifier = Modifier.padding(16.dp, 10.dp),
                                text = viewModel.category,
                                color = Color.White
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
                                    .background(MainBackgroundColor),
                                colors= ButtonDefaults.buttonColors(backgroundColor = (Color.Gray))
                            ) {

                            }
                        }else{
                            Button(
                                onClick = { },
                                modifier = modifier
                                    .background(MainBackgroundColor),
                                colors= ButtonDefaults.buttonColors(backgroundColor = (Color(viewModel.priorityColor.toColorInt())))

                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}