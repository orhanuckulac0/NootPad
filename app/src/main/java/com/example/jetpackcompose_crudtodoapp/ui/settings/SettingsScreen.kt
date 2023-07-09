package com.example.jetpackcompose_crudtodoapp.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainBackgroundColor
import com.example.jetpackcompose_crudtodoapp.ui.theme.MainTextColor
import com.example.jetpackcompose_crudtodoapp.ui.util.UiEvent
import com.example.jetpackcompose_crudtodoapp.ui.util.data_store.StoreThemePref

@Composable
fun SettingsScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val dataStore = StoreThemePref(context)
    val storedTheme = dataStore.getData.collectAsState(initial = false).value
    var isDarkTheme by remember { mutableStateOf(storedTheme) }

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else-> Unit
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MainBackgroundColor,
                title = { Text(
                    text = "Settings",
                    color = MainTextColor )
                },
            )
        }, content = {
            it.calculateTopPadding()
            Column(
                modifier = Modifier
                    .background(MainBackgroundColor)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 15.dp))
                {
                    Text(text = "Switch Theme")
                    Spacer(modifier = Modifier.width(20.dp))
                    ThemeSwitcher(
                        size = 50.dp,
                        padding = 5.dp,
                        isDarkTheme = isDarkTheme!!,
                        onThemeUpdated = { isDarkTheme = !isDarkTheme!! }
                    )
                }
            }
        }
    )
}