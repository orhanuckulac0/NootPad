package com.example.jetpackcompose_crudtodoapp.ui.util.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreThemePref(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("themePref")
        val THEME = booleanPreferencesKey("theme_pref")
    }
    val getData: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[THEME] ?: false
        }

    suspend fun saveData(theme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME] = theme
        }
    }

}