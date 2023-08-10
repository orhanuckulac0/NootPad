package com.example.jetpackcompose_crudtodoapp.ui.util

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.jetpackcompose_crudtodoapp.data.data_source.TodoDatabase
import com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager.setAlarm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DataRetrievalService : Service() {

    @Inject
    lateinit var todoDatabase: TodoDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startDataRetrieval()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startDataRetrieval() {
        val dataStoreHelper = AlarmDataStore(this)

        CoroutineScope(Dispatchers.IO).launch {
            dataStoreHelper.getDataUnique.collect { key ->
                if (key.isNotEmpty()){
                    key.forEach {
                        val parts = it.name.split('-')

                        val todoId = parts[0].toInt()
                        val alarmTime = parts[1]
                        val dueDate = parts[2]

                        val todo = todoDatabase.todoDAO.getTodoByID(todoId)
                        if (todo!!.isAlarmSet == false) {
                            setAlarm(this@DataRetrievalService, todoId, dueDate, alarmTime)
                            todoDatabase.todoDAO.insertTodo(todo.copy(
                                isAlarmSet = true,
                                alarmDate = alarmTime
                            ))
                        } else {
                            stopSelf()
                        }
                    }
                }
                stopSelf()
            }
            stopSelf()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

