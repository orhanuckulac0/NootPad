package com.example.jetpackcompose_crudtodoapp.ui.alarms

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.jetpackcompose_crudtodoapp.domain.model.TodoEntity
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectTodoDialog(
    todos: List<TodoEntity>,
    onDismiss: () -> Unit
){
    val timeDialogState = rememberMaterialDialogState()
    val pickedTime = remember { mutableStateOf(LocalTime.now()) }


    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {

        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxSize(0.9f)
                .border(2.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 10.dp)
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(text = Constants.TODO, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(text = Constants.ADD_ALARM, fontWeight = FontWeight.Bold)
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                )
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    itemsIndexed(todos) { _, todo ->
                        if (todo.isAlarmSet != true){
                            Row(modifier = Modifier.fillMaxWidth()) {
                                SingleAlarmRow(todo, timeDialogState, pickedTime)
                            }
                        }
                    }
                }
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth(0.3f)
                        .padding(vertical = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White)
                ) {
                    Text(text = Constants.CLOSE)
                }
            }
        }

    }
}
