package com.example.jetpackcompose_crudtodoapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryModel(
    val title: String,
    val image: ImageVector,
    val color: Color
)
