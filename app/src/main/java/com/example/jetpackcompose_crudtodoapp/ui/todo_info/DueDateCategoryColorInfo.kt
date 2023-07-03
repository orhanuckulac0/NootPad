package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.WhiteBackground

@Composable
fun DueDateCategoryColorInfo(
    viewModel: TodoInfoViewModel
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .shadow(10.dp, ambientColor = Color.Black, spotColor = Color.Black)
                .clip(RoundedCornerShape(5.dp))
                .background(WhiteBackground)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = viewModel.dueDate,
                color = MainTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        Box(
            modifier = Modifier
                .shadow(10.dp, ambientColor = Color.Black, spotColor = Color.Black)
                .clip(RoundedCornerShape(5.dp))
                .background(WhiteBackground)
                .size(100.dp, 40.dp)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = viewModel.category,
                color = MainTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        if (viewModel.priorityColor == ""){
            Box(
                modifier = Modifier
                    .shadow(10.dp, ambientColor = Color.Black, spotColor = Color.Black)
                    .clip(RoundedCornerShape(5.dp))
                    .size(110.dp, 43.dp)
                    .background(Color.Gray)
                    .padding(10.dp)
            )
        }else{
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(viewModel.priorityColor.toColorInt()))
                    .size(100.dp, 40.dp)
                    .padding(10.dp)
            )
        }
    }
}