package com.example.jetpackcompose_crudtodoapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

object Routes {
    const val TODO_LIST = "todo_list"
    const val ADD_TODO = "add_todo"
    const val EDIT_TODO = "edit_todo"
    const val TODO_INFO = "todo_info"
    fun NavOptionsBuilder.popUpToTop(navController: NavController) {
        popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
            inclusive =  true
        }
    }

}