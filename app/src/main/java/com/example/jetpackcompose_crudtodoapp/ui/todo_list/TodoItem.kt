package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
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
        Column(modifier = Modifier.weight(0.1f)){
            Button(
                onClick = { },
                modifier = modifier
                    .background((Color.White))
                    .clip(RoundedCornerShape(20.dp))
                    .requiredSize(20.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                shape = CircleShape,
                colors= ButtonDefaults.buttonColors(backgroundColor = (Color(todo.priorityColor.toColorInt())))
            ){

            }
        }

        Column(
            modifier = Modifier.weight(0.9f).padding(10.dp,0.dp,0.dp,0.dp),
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
