package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmSection(
    modifier: Modifier,
    viewModel: AddEditTodoViewModel,
    timeDialogState: MaterialDialogState,
    pickedTime: MutableState<LocalTime>,
){
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(Constants.OK){
                viewModel.onEvent(AddEditTodoEvent.OnTimeDateChange(pickedTime.value.toString()))
            }
            negativeButton(Constants.CANCEL){
                viewModel.onEvent(AddEditTodoEvent.OnTimeDateChange(null))
            }
        }
    ) {

        timepicker { time ->
            pickedTime.value = time
        }

    }

    Column {
        Row {
            OutlinedButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnTimePickerClicked)
            }, modifier = modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                Text(
                    text = "Set Alarm Time",
                    color = Color.Black,
                )
            }

            if (viewModel.alarmDate == null){
                Text(
                    modifier = modifier.padding(30.dp, 10.dp),
                    text = "Optional",
                    color = Color.White
                )
            }else{
                Text(
                    modifier = modifier.padding(30.dp, 10.dp),
                    text = viewModel.alarmDate.toString(),
                    color = Color.White
                )
            }
        }
    }
}