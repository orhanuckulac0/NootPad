package com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
    focusRequester: FocusRequester,
    customTextSelectionColors: TextSelectionColors
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
                color = Color.Gray,
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
                CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors){
                    TextField(
                        value = viewModel.description,
                        onValueChange = {
                            if (it.length <= descriptionMaxCharLength) {
                                viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                            }
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .shadow(10.dp, ambientColor = Color.Black, spotColor = Color.Black)
                            .clip(RoundedCornerShape(5.dp))
                            .focusRequester(focusRequester)
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            disabledTextColor = Color.Transparent,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        singleLine = false,
                        maxLines = 15
                    )
                }
                Text(
                    text = "${viewModel.description.length} / $descriptionMaxCharLength",
                    textAlign = TextAlign.End,
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
                )
            }
        }
    }
    Spacer(modifier = modifier.height(8.dp))

}