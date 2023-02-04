package com.example.jetpackcompose_crudtodoapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose_crudtodoapp.navigation.Routes.popUpToTop
import com.example.jetpackcompose_crudtodoapp.ui.add_todo.AddTodoScreen
import com.example.jetpackcompose_crudtodoapp.ui.edit_todo.EditTodoScreen
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
                TodoScreen(
                    onNavigate = {
                        navController.navigate(it.route)
                    }
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
                TodoInfoScreen(onPopBackStack = {
                    navController.popBackStack()
                }, onNavigate = {
                    navController.navigate(it.route)
                })
            }
            composable(
                route = Routes.ADD_TODO
            ) {
                AddTodoScreen(
                    onPopBackStack = {
                        navController.popBackStack()
                                     },
                    onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(Routes.TODO_LIST) {
                            inclusive = true
                        }
                    }
                })
            }
            composable(
                route = Routes.EDIT_TODO+"?todoId={todoId}",
                arguments = listOf(
                    navArgument(name = "todoId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ){
                EditTodoScreen(onPopBackStack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(it.route) {
                            popUpTo(Routes.TODO_LIST)
                        }
                    })
            }
        }
    )
}