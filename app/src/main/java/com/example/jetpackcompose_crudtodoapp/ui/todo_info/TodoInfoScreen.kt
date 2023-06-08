package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.R
import com.example.jetpackcompose_crudtodoapp.ui.theme.DarkBlue
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.util.Constants
import com.example.jetpackcompose_crudtodoapp.util.CustomDialog
import com.example.jetpackcompose_crudtodoapp.util.UiEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoInfoScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoInfoViewModel = hiltViewModel(),
    shouldShowDialog: MutableState<Boolean>,
    scrollableDescription: ScrollState,
    scaffoldState: ScaffoldState
) {

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
                title = { Text(text = Constants.TODO_DETAILS) },
                navigationIcon = {
                    IconButton(onClick = {
                        onPopBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
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
                             tint = Color.White
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
                        viewModel.onEvent(TodoInfoEvent.OnEditTodoClick)
                    },
                    backgroundColor = DarkBlue
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = Constants.EDIT_TODO,
                        tint= colorResource(id = R.color.white)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(
                    backgroundColor = DarkBlue,
                    cutoutShape = CircleShape) {
                }
            }
        ) {
            it.calculateBottomPadding()
            it.calculateTopPadding()
            Column(modifier = modifier
                .fillMaxSize()
                .background(MainBackgroundColor)
                .padding(16.dp)
            ) {
                Column(modifier = modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Row {
                        Text(
                            text = Constants.TODO_TITLE,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.height(8.dp))

                    Row {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .fillMaxWidth()
                                    .background(DarkBlue)
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = viewModel.title, color = Color.White, fontWeight = FontWeight.Light, fontSize = 24.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = modifier.height(8.dp))

                Column(modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Row {
                        Text(
                            text = Constants.TODO_DESCR,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.height(8.dp))

                    Row {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 360.dp, height = 360.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(DarkBlue)
                                    .padding(10.dp, 10.dp, 10.dp, 0.dp),
                            ){
                                Text(
                                    modifier = modifier.verticalScroll(scrollableDescription),
                                    text = viewModel.description,
                                    color = Color.White,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 20.sp)
                            }
                        }
                    }
                }

                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = Constants.DUE_DATE,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                        Text(
                            modifier = modifier.padding(0.dp, 0.dp, 15.dp, 0.dp),
                            text = Constants.CATEGORY,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                        Text(
                            modifier = modifier.padding(0.dp, 0.dp, 20.dp, 5.dp),
                            text = Constants.COLOR,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkBlue)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = viewModel.dueDate,
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkBlue)
                                .size(100.dp, 40.dp)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = viewModel.category,
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                        if (viewModel.priorityColor == ""){
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .size(110.dp, 43.dp)
                                    .background(Color.Gray)
                                    .padding(10.dp)
                            )
                        }else{
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(viewModel.priorityColor.toColorInt()))
                                    .size(100.dp, 40.dp)
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
