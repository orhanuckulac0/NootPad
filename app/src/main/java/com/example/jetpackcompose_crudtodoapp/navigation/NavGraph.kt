package com.example.jetpackcompose_crudtodoapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose_crudtodoapp.ui.add_edit_todo.AddEditTodoScreen
import com.example.jetpackcompose_crudtodoapp.ui.todo_info.TodoInfoScreen
import com.example.jetpackcompose_crudtodoapp.ui.todo_list.TodoScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = {
            composable(
                route = Routes.TODO_LIST
            ){
                val shouldShowDialogTS = remember { mutableStateOf(false) }

                TodoScreen(
                    onNavigate = {
                        navController.navigate(it.route)
                    },
                    shouldShowDialog = shouldShowDialogTS
                )
            }
            composable(
                route = Routes.TODO_INFO+"?todoId={todoId}",
                arguments = listOf(
                    navArgument(name = "todoId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ){
                val shouldShowDialogTIS = remember { mutableStateOf(false) }
                val scrollableDescription = rememberScrollState()
                val scaffoldState: ScaffoldState = rememberScaffoldState()

                TodoInfoScreen(
                    onPopBackStack = {
                    navController.popBackStack()
                                     },
                    onNavigate = {
                    navController.navigate(it.route) },
                    shouldShowDialog = shouldShowDialogTIS,
                    scrollableDescription = scrollableDescription,
                    scaffoldState = scaffoldState
                )
            }
            composable(
                route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                arguments = listOf(
                    navArgument(name = "todoId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                val scaffoldState: ScaffoldState = rememberScaffoldState()
                AddEditTodoScreen(
                    onPopBackStack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.navigate(it.route) {
                            popUpTo(Routes.TODO_LIST)
                        }
                    },
                    scaffoldState = scaffoldState
                )
            }
        }
    )
}