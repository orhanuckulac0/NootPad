package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_crudtodoapp.ui.theme.DarkBlue
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants

@Composable
fun TodoTitleSection(
    modifier: Modifier,
    viewModel: TodoInfoViewModel
){
    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Row {
            Text(
                text = Constants.TODO_TITLE,
                color = Color.LightGray,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = modifier.height(8.dp))

        Row {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(DarkBlue)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = viewModel.title, color = Color.White, fontWeight = FontWeight.Light, fontSize = 24.sp)
                }
            }
        }
    }

    Spacer(modifier = modifier.height(8.dp))

}