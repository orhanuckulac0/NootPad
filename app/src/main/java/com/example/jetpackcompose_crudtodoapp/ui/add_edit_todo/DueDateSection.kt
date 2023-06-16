package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DueDateSection(
    modifier: Modifier,
    viewModel: AddEditTodoViewModel,
    dateDialogState: MaterialDialogState,
    formattedDate: State<String>,
    pickedDate: MutableState<LocalDate>
){
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = Constants.OK, onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnDueDateChange(formattedDate.value))
            })
            negativeButton(text = Constants.CANCEL)
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = Constants.PICK_A_DATE,
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colors.primary,
                dateActiveBackgroundColor = MaterialTheme.colors.primary,
            ),
            allowedDateValidator = {
                val yesterdayDate = LocalDate.now().minusDays(1)
                it.isAfter(yesterdayDate)
            }
        ) {
            pickedDate.value = it
        }
    }

    Column {
        Row {
            OutlinedButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnDatePickerClick)
            }, modifier = modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                Text(
                    text = Constants.SELECT_DUE_DATE,
                    color = Color.Black,
                )
            }

            if (viewModel.dueDate != "") {
                Text(
                    modifier = modifier.padding(30.dp, 10.dp),
                    text = viewModel.dueDate,
                    color = Color.White

                )
            } else {
                Text(
                    modifier = modifier.padding(30.dp, 10.dp),
                    text = Constants.NOT_SELECTED_YET,
                    color = Color.White
                )
            }
        }
    }
}