package com.example.jetpackcompose_crudtodoapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

object Routes {
    const val TODO_LIST = "todo_list"
    const val TODO_INFO = "todo_info"
    const val ADD_EDIT_TODO = "add_edit_todo_info"
    const val SETTINGS = "settings"
    const val ALARMS = "alarms"

    fun NavOptionsBuilder.popUpToTop(navController: NavController) {
        popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
            inclusive =  true
        }
    }

}