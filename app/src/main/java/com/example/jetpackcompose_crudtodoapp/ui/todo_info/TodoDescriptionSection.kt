package com.example.jetpackcompose_crudtodoapp.ui.todo_info

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.jetpackcompose_crudtodoapp.util.Constants

@Composable
fun TodoDescriptionSection(
    modifier: Modifier,
    viewModel: TodoInfoViewModel,
    scrollableDescription: ScrollState
){
    Column(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(0.65f)
        .padding(0.dp, 10.dp, 0.dp, 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            Text(
                text = Constants.TODO_DESCR,
                color = Color.LightGray,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = modifier.height(8.dp))

        Row {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 360.dp, height = 360.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(DarkBlue)
                        .padding(10.dp, 10.dp, 10.dp, 0.dp),
                ){
                    Text(
                        modifier = modifier.verticalScroll(scrollableDescription),
                        text = viewModel.description,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp)
                }
            }
        }
    }

}