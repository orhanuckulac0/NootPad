package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.WhiteBackground

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
        IconButton(
            onClick = {
                onEvent(TodoEvent.OnDoneChange(todo, !todo.isDone))
                      },
            modifier = Modifier.offset(x = 0.dp, y = 4.dp),
        ) {

            Icon(
                imageVector = if (todo.isDone) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                contentDescription = "Checkbox",
                tint = (Color(todo.priorityColor.toColorInt())),
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(0.9f)
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                .background(WhiteBackground),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
                ) {
                if (todo.isDone){
                    Text(
                        text = todo.title,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MainTextColor
                    )
                }else{
                    Text(
                        text = todo.title,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MainTextColor
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            todo.dueDate.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =it,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}