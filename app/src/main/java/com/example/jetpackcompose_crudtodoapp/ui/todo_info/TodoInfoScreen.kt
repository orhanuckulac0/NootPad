package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
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
import com.example.jetpackcompose_crudtodoapp.util.CustomDialog
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

    var shouldShowDialog by remember { mutableStateOf(false) }
    if (shouldShowDialog){
        CustomDialog(
            setShowDialog =  {
                shouldShowDialog = it
            },
            deletedFromScreen = "TodoInfoScreen"
        )
    }

    val scaffoldState: ScaffoldState = rememberScaffoldState()
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
                title = { Text(text = "Todo Details") },
                navigationIcon = {
                    IconButton(onClick = {
                        onPopBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate Back",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        shouldShowDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
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
                        contentDescription = "Edit Todo",
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

                Column() {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(0.dp, 5.dp,0.dp, 0.dp)
                    ) {
                        Text(
                            text = "Due Date",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                        Text(
                            modifier = modifier.padding(0.dp, 0.dp, 5.dp, 5.dp),
                            text = "Priority Color",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
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
                                fontWeight = FontWeight.Light,
                                fontSize = 18.sp
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
                                    .size(110.dp, 43.dp)

                                    .background(Color(viewModel.priorityColor.toColorInt()))
                                    .padding(10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = modifier.height(8.dp))

                Column(modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
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
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 360.dp, height = 360.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(DarkBlue)
                                    .padding(10.dp, 10.dp, 10.dp, 10.dp),
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
            }
        }
    }
}
