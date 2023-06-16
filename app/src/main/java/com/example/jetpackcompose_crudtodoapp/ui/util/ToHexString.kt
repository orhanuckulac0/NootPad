package com.example.jetpackcompose_crudtodoapp.ui.util

import androidx.compose.ui.graphics.Color

fun toHexString(color: Color): String {
    return String.format(
        "#%02x%02x%02x%02x", (color.alpha * 255).toInt(),
        (color.red * 255).toInt(), (color.green * 255).toInt(), (color.blue * 255).toInt()
    )
}
