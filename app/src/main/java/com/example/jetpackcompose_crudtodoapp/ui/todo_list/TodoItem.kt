package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_crudtodoapp.data.TodoEntity

@Composable
fun TodoItem(
    todo: TodoEntity,
    onEvent: (TodoEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
                ) {
                Text(
                    text = todo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(8.dp))
//                IconButton(onClick = {
//                    onEvent(TodoEvent.OnDeleteTodoClick(todo))
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Delete",
//                        tint = Color.Red
//
//                    )
//                }
            }
            todo.description.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { isChecked ->
                onEvent(TodoEvent.OnDoneChange(todo, isChecked))
            },
        )
    }
}
