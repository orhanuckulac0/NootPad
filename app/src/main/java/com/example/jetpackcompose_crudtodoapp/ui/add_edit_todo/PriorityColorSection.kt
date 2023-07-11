package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants
import com.example.jetpackcompose_crudtodoapp.ui.util.toHexString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.color.colorChooser

@Composable
fun PriorityColorSection(
    colorPickerDialogState: MaterialDialogState,
    modifier: Modifier,
    viewModel: AddEditTodoViewModel,
    pickedColor : MutableState<String>
){
    val listOfColors = listOf(
        Color(0xFF808080),
        Color(0xFF000000),
        Color(0xFFFF0000),
        Color(0xFF0000FF),
        Color(0xFF00FF00),
        Color(0xFF30D5C8),
        Color(0xFFA020F0),
        Color(0xFF964B00),
        Color(0xFF514b9a),
    )

    MaterialDialog(
        dialogState = colorPickerDialogState,
        buttons = {
            positiveButton(text = Constants.SAVE, onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnPriorityColorChange(pickedColor.value))
            })
            negativeButton(text = Constants.CANCEL, onClick = {})
        }
    ) {
        colorChooser(
            colors = listOfColors,
            onColorSelected = {
                pickedColor.value = toHexString(it)
            }
        )
    }

    Column {
        Row {
            OutlinedButton(onClick = {
                colorPickerDialogState.show()
            }, modifier = modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                Text(
                    text = Constants.SELECT_PRIORITY_COLOR,
                    color = Color.Black
                )
            }

            if (viewModel.priorityColor == "") {
                Button(
                    onClick = { },
                    modifier = modifier
                        .background(MainBackgroundColor),
                    colors = ButtonDefaults.buttonColors(backgroundColor = (Color.Gray))
                ) {

                }
            } else {
                Button(
                    onClick = { },
                    modifier = modifier
                        .background(MainBackgroundColor),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = (Color(
                            viewModel.priorityColor.toColorInt()
                        ))
                    )
                ) {

                }
            }
        }
    }
}