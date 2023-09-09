package com.example.jetpackcompose_crudtodoapp.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.example.jetpackcompose_crudtodoapp.ui.alarms.AlarmViewModel
import com.example.jetpackcompose_crudtodoapp.ui.theme.BlueColor
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDatePicker(
    viewModel: AlarmViewModel,
    dateDialogState: MaterialDialogState,
    timeDialogState: MaterialDialogState,
    formattedDate: State<String>,
    pickedDate: MutableState<LocalDate>
){
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = Constants.OK, onClick = {
                viewModel.updatedDueDate = formattedDate.value
                timeDialogState.show()
            })
            negativeButton(text = Constants.CANCEL){
                viewModel.updatedDueDate = ""
            }
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = Constants.PICK_A_DATE,
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = BlueColor,
                dateActiveBackgroundColor = BlueColor,
            ),
            allowedDateValidator = {
                val yesterdayDate = LocalDate.now().minusDays(1)
                it.isAfter(yesterdayDate)
            }
        ) {
            pickedDate.value = it
        }
    }

}