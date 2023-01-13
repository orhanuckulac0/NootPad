package com.example.jetpackcompose_crudtodoapp.util

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
import com.example.jetpackcompose_crudtodoapp.ui.todo_list.TodoEvent
import com.example.jetpackcompose_crudtodoapp.ui.todo_list.TodoViewModel

@Composable
fun CustomDialog(
    setShowDialog: (Boolean) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
){
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280 .dp, 240.dp)
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 0.dp, 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Are you sure to delete this?",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Row {
                    OutlinedButton(
                        onClick = {
                            viewModel.onEvent(
                            TodoEvent.OnDeleteTodoClick(
                                viewModel.deletedTodo!!)
                            )
                            setShowDialog(false)
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(
                            text = "Delete",
                            fontSize = 16.sp,
                            color = Color.Black,
                        )
                    }

                    Button(
                        onClick = {
                            // after cancel set it to null again
                            viewModel.onEvent(TodoEvent.OnSetTodoToDeleteToNull)
                            setShowDialog(false)
                                  },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)

                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

}