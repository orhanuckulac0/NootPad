package com.example.jetpackcompose_crudtodoapp.ui.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("alarmStored")
        val todoId = intPreferencesKey("todoId")
        val todoAlarmTime = stringPreferencesKey("todoAlarmTime")
        val todoAlarmDate = stringPreferencesKey("todoAlarmDate")
    }

    val getData: Flow<Triple<Int, String?, String?>> = context.dataStore.data
        .map { preferences ->
            val id = preferences[todoId] ?: 0
            val time = preferences[todoAlarmTime]
            val date = preferences[todoAlarmDate]
            Triple(id, time, date)
        }


    suspend fun saveData(id: Int, alarmTime: String, alarmDate: String,) {
        context.dataStore.edit { preferences ->
            preferences[todoId] = id
            preferences[todoAlarmTime] = alarmTime
            preferences[todoAlarmDate] = alarmDate
        }
    }
}
