package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.ui.theme.BlueColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditTodoScreen(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
    ){

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Blue,
        backgroundColor =  Color.Blue.copy(alpha = 0.2f)
    )

    val mDropdownSize = remember { mutableStateOf(Size.Zero) }
    val mExpanded = remember { mutableStateOf(false) }

    val pickedColor = remember { mutableStateOf("") }
    val colorPickerDialogState = rememberMaterialDialogState()

    val dateDialogState = rememberMaterialDialogState()
    val pickedDate = remember { mutableStateOf(LocalDate.now()) }
    val formattedDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(Constants.DATE_FORMAT)
                .format(pickedDate.value)
        }
    }

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
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            focusManager.clearFocus()
        },

        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MainBackgroundColor,
                title = { Text(text = viewModel.topAppBarText, color = MainTextColor) },
                navigationIcon = {
                    IconButton(onClick = {
                        onPopBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = BlueColor,
                            contentDescription = Constants.NAVIGATE_BACK,
                        )
                    }
                }
            )
        },
        content = {
            it.calculateTopPadding()
            it.calculateBottomPadding()
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MainBackgroundColor)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
            ) {
                item{
                    TitleSection(
                        modifier = modifier,
                        viewModel = viewModel,
                        focusRequester = focusRequester,
                        customTextSelectionColors = customTextSelectionColors
                    )
                }

                item{
                    DescriptionSection(
                        modifier = modifier,
                        viewModel = viewModel,
                        focusRequester = focusRequester,
                        customTextSelectionColors = customTextSelectionColors
                    )
                }
                item{
                    DueDateSection(
                        modifier = modifier,
                        viewModel = viewModel,
                        dateDialogState = dateDialogState,
                        formattedDate = formattedDate,
                        pickedDate = pickedDate
                    )
                }

                item{
                    CategorySection(
                        viewModel = viewModel,
                        mDropdownSize = mDropdownSize,
                        mExpanded = mExpanded
                    )

                }
                item{
                    PriorityColorSection(
                        colorPickerDialogState = colorPickerDialogState,
                        modifier = modifier,
                        viewModel = viewModel,
                        pickedColor = pickedColor
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
                },
                backgroundColor = BlueColor
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = Constants.SAVE,
                    tint = colorResource(id = R.color.white)
                )
            }
        }
    )
}