package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.ui.theme.BlueColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.WhiteBackground
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.example.jetpackcompose_crudtodoapp.ui.util.CustomDialog
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoInfoScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoInfoViewModel = hiltViewModel(),
    shouldShowDialog: MutableState<Boolean>,
) {

    val scrollableDescription = rememberScrollState()

    if (shouldShowDialog.value){
        CustomDialog(
            setShowDialog =  {
                shouldShowDialog.value = it
            },
            deletedFromScreen = "TodoInfoScreen"
        )
    }

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
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
                backgroundColor = MainBackgroundColor,
                title = { Text(text = Constants.TODO_DETAILS, color = MainTextColor) },
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
                },
                actions = {
                    IconButton(onClick = {
                        shouldShowDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = Constants.DELETE,
                             tint = BlueColor
                        )
                    }
                }
            )
        },
        content = {
            it.calculateBottomPadding()
            it.calculateTopPadding()
            Column(modifier = modifier
                .fillMaxSize()
                .background(MainBackgroundColor)
                .padding(16.dp)
            ) {

                TodoTitleSection(modifier = modifier, viewModel = viewModel)

                TodoDescriptionSection(
                    modifier = modifier,
                    viewModel = viewModel,
                    scrollableDescription = scrollableDescription
                )

                Column {
                    DueDateCategoryColorTitles(modifier = modifier)

                    DueDateCategoryColorInfo(viewModel = viewModel)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    viewModel.onEvent(TodoInfoEvent.OnEditTodoClick)
                },
                backgroundColor = BlueColor
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = Constants.EDIT_TODO,
                    tint= colorResource(id = R.color.white)
                )
            }
        }
    )
}


