package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.ui.theme.DarkBlue
import com.example.jetpackcompose_crudtodoapp.util.Constants

@Composable
fun ChipSection(
    viewModel: TodoViewModel = hiltViewModel(),
) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    val categories = listOf("All", "Home", "School", "Work", "Sports", "Fun", "Friends", "Grocery", "Other")

    Column {
        Row(modifier = Modifier.padding(15.dp,15.dp,0.dp,0.dp)) {
            Text(text = Constants.CATEGORIES, color= Color.LightGray, fontWeight = FontWeight.Medium, fontSize = 14.sp)
        }
        LazyRow {
            items(categories.size) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                        .clickable {
                            selectedChipIndex = it
                            viewModel.selectedCategory.value = categories[it]
                            viewModel.onEvent(TodoEvent.OnCategoryClicked(categories[it]))
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (selectedChipIndex == it) Color.White
                            else DarkBlue
                        )
                        .padding(15.dp)
                ) {
                    if (selectedChipIndex == it){
                        Text(
                            text = categories[it],
                            color =  DarkBlue
                        )
                    }else{
                        Text(
                            text = categories[it],
                            color =  Color.White
                        )
                    }
                }
            }
        }

    }
}
