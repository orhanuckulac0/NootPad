package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants

@Composable
fun DueDateCategoryColorTitles(
    modifier: Modifier,
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = Constants.DUE_DATE,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
        Text(
            modifier = modifier.padding(0.dp, 0.dp, 15.dp, 0.dp),
            text = Constants.CATEGORY,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
        Text(
            modifier = modifier.padding(0.dp, 0.dp, 20.dp, 5.dp),
            text = Constants.PRIORITY,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
    }
}