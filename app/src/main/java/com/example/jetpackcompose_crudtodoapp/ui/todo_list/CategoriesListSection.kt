package com.example.jetpackcompose_crudtodoapp.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.domain.model.CategoryModel
import com.example.jetpackcompose_crudtodoapp.ui.theme.WhiteBackground
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants

@Composable
fun CategorySection(
    viewModel: TodoViewModel = hiltViewModel(),
    selectedChipIndex: MutableState<Int>
) {
    val categories = listOf(
        CategoryModel(Constants.ALL, Icons.Default.SelectAll, Color.Gray),
        CategoryModel(Constants.HOME, Icons.Default.Home, Color.Gray),
        CategoryModel(Constants.SCHOOL, Icons.Default.School, Color.Gray),
        CategoryModel(Constants.WORK, Icons.Default.Work, Color.Gray),
        CategoryModel(Constants.SPORTS, Icons.Default.Sports, Color.Gray),
        CategoryModel(Constants.FUN, Icons.Default.Cyclone, Color.Gray),
        CategoryModel(Constants.FRIENDS, Icons.Default.Person, Color.Gray),
        CategoryModel(Constants.GROCERY, Icons.Default.LocalGroceryStore, Color.Gray),
        CategoryModel(Constants.OTHER, Icons.Default.Clear, Color.Gray),
        )


    Column {
        Row(modifier = Modifier.padding(15.dp,15.dp,0.dp,0.dp)) {
            Text(text = Constants.CATEGORIES, color= Color.Gray, fontWeight = FontWeight.Medium, fontSize = 14.sp)
        }
        LazyRow(
            modifier = Modifier.padding(end = 15.dp)
        ) {
            items(categories.size) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                        .shadow(
                            elevation = if (selectedChipIndex.value == it) 10.dp else 0.dp,
                            ambientColor = if (selectedChipIndex.value == it) Color.Black else Color.White,
                            spotColor = if (selectedChipIndex.value == it) Color.Black else Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))

                        .clickable {
                            selectedChipIndex.value = it
                            viewModel.selectedCategory.value = categories[it].title
                            viewModel.onEvent(TodoEvent.OnCategoryClicked(categories[it].title))
                        }
                        .background(
                            if (selectedChipIndex.value == it) Color.White
                            else WhiteBackground
                        )
                        .padding(25.dp)

                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val iconColor = if (selectedChipIndex.value == it) Color.Blue else Color.Gray
                        Icon(
                            painter = rememberVectorPainter(image = categories[it].image),
                            contentDescription = Constants.ICON,
                            tint = iconColor,
                            modifier = Modifier.size(24.dp)
                        )
                        if (selectedChipIndex.value == it){
                            Text(
                                text = categories[it].title,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }else{
                            Text(
                                text = categories[it].title,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}