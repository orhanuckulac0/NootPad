package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants

@Composable
fun CategorySection(
    viewModel: AddEditTodoViewModel,
    mExpanded: MutableState<Boolean>,
    mDropdownSize: MutableState<Size>
){
    val mCategories = listOf("Home", "School", "Work", "Sports", "Fun", "Friends", "Other")

    val icon = if (mExpanded.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        Row {
            OutlinedButton(onClick = {
                mExpanded.value = !mExpanded.value
            }, modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    mDropdownSize.value = coordinates.size.toSize()
                }
                .clickable { mExpanded.value = !mExpanded.value }
            ) {
                Text(
                    Constants.SELECT_CATEGORY,
                    color = Color.Black,
                )
                Icon(
                    imageVector = icon,
                    contentDescription = "dropdownIcon",
                    Modifier.clickable { mExpanded.value = !mExpanded.value }
                )
            }

            DropdownMenu(
                expanded = mExpanded.value,
                onDismissRequest = { mExpanded.value = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mDropdownSize.value.width.toDp() })
            ) {
                mCategories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        viewModel.onEvent(AddEditTodoEvent.OnCategoryChange(category))
                        mExpanded.value = false
                    }) {
                        Text(text = category)
                    }
                }
            }

            if (viewModel.category == "") {
                Text(
                    modifier = Modifier.padding(16.dp, 10.dp),
                    text = Constants.NOT_SELECTED_YET,
                    color = Color.White

                )
            } else {
                Text(
                    modifier = Modifier.padding(16.dp, 10.dp),
                    text = viewModel.category,
                    color = Color.White
                )
            }
        }
    }

}