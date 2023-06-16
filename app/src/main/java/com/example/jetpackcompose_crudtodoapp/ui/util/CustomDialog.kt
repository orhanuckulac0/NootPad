package com.example.jetpackcompose_crudtodoapp.ui.util

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.ui.theme.DarkBlue
import com.example.jetpackcompose_crudtodoapp.ui.todo_info.TodoInfoEvent
import com.example.jetpackcompose_crudtodoapp.ui.todo_info.TodoInfoViewModel
import com.example.jetpackcompose_crudtodoapp.ui.todo_list.TodoEvent
import com.example.jetpackcompose_crudtodoapp.ui.todo_list.TodoViewModel

@Composable
fun CustomDialog(
    setShowDialog: (Boolean) -> Unit,
    todoScreenViewModel: TodoViewModel = hiltViewModel(),
    todoInfoViewModel: TodoInfoViewModel = hiltViewModel(),
    deletedFromScreen: String
){
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Constants.DELETE_QUESTION,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Row {
                    Button(
                        onClick = {
                            setShowDialog(false)
                                  },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Text(
                            text = Constants.CANCEL,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            if (deletedFromScreen == "TodoScreen"){
                                todoScreenViewModel.onEvent(
                                    TodoEvent.OnDeleteTodoClick(
                                        todoScreenViewModel.deletedTodo!!)
                                )
                            }else{
                                todoInfoViewModel.onEvent(
                                    TodoInfoEvent.OnDeleteTodoClick(
                                        todoInfoViewModel.todoEntity!!)
                                )
                            }
                            setShowDialog(false)
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue)
                    ) {
                        Text(
                            text = Constants.DELETE,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}