package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants

@Composable
fun DescriptionSection(
    modifier: Modifier,
    viewModel: AddEditTodoViewModel,
){
    val descriptionMaxCharLength = 1000

    Column(
        modifier = modifier
            .fillMaxWidth(),
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
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    value = viewModel.description,
                    onValueChange = {
                        if (it.length <= descriptionMaxCharLength) {
                            viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                        }
                    },
                    label = { Text(Constants.DESCR) },
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    singleLine = false,
                    maxLines = 15
                )
                Text(
                    text = "${viewModel.description.length} / $descriptionMaxCharLength",
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