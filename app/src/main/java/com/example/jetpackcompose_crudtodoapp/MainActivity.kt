package com.example.jetpackcompose_crudtodoapp

import android.app.NotificationManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose_crudtodoapp.ui.navigation.NavGraph
import com.example.jetpackcompose_crudtodoapp.ui.theme.JetpackComposeCRUDTodoAppTheme
import com.example.jetpackcompose_crudtodoapp.ui.navigation.Routes
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants.NOTIFICATION_ID
import com.example.jetpackcompose_crudtodoapp.ui.util.Constants.NOTIFICATION_INT
import com.example.jetpackcompose_crudtodoapp.ui.util.LockScreenOrientation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCRUDTodoAppTheme {
                LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                val navController = rememberNavController()
                NavGraph(navController = navController, startDestination = Routes.TODO_LIST)
            }
            if (intent?.extras?.getInt(NOTIFICATION_ID) == NOTIFICATION_INT) {
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(NOTIFICATION_INT)
            }
        }
    }
}