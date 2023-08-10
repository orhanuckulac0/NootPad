package com.example.jetpackcompose_crudtodoapp.ui.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("alarmStored")
    }

    suspend fun saveDataUnique(id: Int, alarmTime: String, alarmDate: String) {
        val uniqueKey = "$id-$alarmTime-$alarmDate"
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(uniqueKey)] = "dataPresent"
        }
    }

    val getDataUnique: Flow<List<Preferences.Key<*>>> = context.dataStore.data
        .map { preferences ->
            preferences.asMap().keys.toList()
        }

    suspend fun deleteDataByKey(key: Preferences.Key<*>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
}
