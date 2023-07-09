package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose_crudtodoapp.ui.theme.BlueColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.SecondaryColor

@Composable
fun DropdownMenuScreen(
    viewModel: TodoViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        MenuItem("Alarms", Icons.Default.Alarm),
        MenuItem("Settings", Icons.Default.Settings),
    )

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More",
                tint = BlueColor
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(5.dp),
        ) {
            menuItems.forEach { menuItem ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    viewModel.onEvent(TodoEvent.OnSettingsClicked)
                }) {
                    Row {
                        Icon(
                            imageVector = menuItem.icon,
                            contentDescription = menuItem.text,
                            tint = SecondaryColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = menuItem.text)
                    }
                }
            }
        }
    }
}

data class MenuItem(
    val text: String,
    val icon: ImageVector
    )
