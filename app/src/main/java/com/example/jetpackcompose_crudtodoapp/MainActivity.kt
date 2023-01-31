package com.example.jetpackcompose_crudtodoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcompose_crudtodoapp.ui.add_todo.AddTodoScreen
import com.example.jetpackcompose_crudtodoapp.ui.edit_todo.EditTodoScreen
import com.example.jetpackcompose_crudtodoapp.ui.theme.JetpackComposeCRUDTodoAppTheme
import com.example.jetpackcompose_crudtodoapp.ui.todo_info.TodoInfoScreen
import com.example.jetpackcompose_crudtodoapp.ui.todo_list.TodoScreen
import com.example.jetpackcompose_crudtodoapp.util.Routes
import com.example.jetpackcompose_crudtodoapp.util.Routes.popUpToTop
import com.example.jetpackcompose_crudtodoapp.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCRUDTodoAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST,
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
                            AddTodoScreen(onNavigate = {
                                navController.navigate(it.route) {
                                    popUpToTop(navController)
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
                            EditTodoScreen(onNavigate = {
                                navController.navigate(it.route) {
                                    popUpTo(Routes.TODO_LIST)
                                }
                            })
                        }
                    }
                )
            }
        }
    }
}