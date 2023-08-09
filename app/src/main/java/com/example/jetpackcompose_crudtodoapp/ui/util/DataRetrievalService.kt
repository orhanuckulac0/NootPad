package com.example.jetpackcompose_crudtodoapp.ui.util

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.jetpackcompose_crudtodoapp.data.data_source.TodoDatabase
import com.example.jetpackcompose_crudtodoapp.ui.alarms.alarm_manager.setAlarm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            dataStoreHelper.getData.collect { (todoId, alarmTime, dueDate) ->
                Log.i("MYTAG", "Todo ID: $todoId, Alarm Date: $alarmTime, Due Date: $dueDate")
                withContext(Dispatchers.IO) {
                    val todo = todoDatabase.todoDAO.getTodoByID(todoId)
                    if (todo!!.isAlarmSet == false){
                        Log.i("MYTAG", "ALARM DELETED BY ANDROID AFTER BOOT")
                        setAlarm(this@DataRetrievalService, todoId, dueDate!!, alarmTime!!)
                        todoDatabase.todoDAO.insertTodo(todo.copy(
                            isAlarmSet = true,
                            alarmDate = alarmTime
                        ))
                    }else{
                        Log.i("MYTAG", "ALARM DIDN'T REMOVED")
                    }
                }
            }

            stopSelf()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

