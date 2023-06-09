package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_crudtodoapp.ui.theme.DarkBlue
import com.example.jetpackcompose_crudtodoapp.util.Constants

@Composable
fun TitleSection(
    modifier: Modifier,
    viewModel: AddEditTodoViewModel,
){
    val titleMaxCharLength = 70

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
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
                TextField(
                    value = viewModel.title,
                    onValueChange = {
                        if (it.length <= titleMaxCharLength) {
                            viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .background(DarkBlue),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                )
                Text(
                    text = "${viewModel.title.length} / $titleMaxCharLength",
                    textAlign = TextAlign.End,
                    color = Color.White,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    Spacer(modifier = modifier.height(8.dp))
}