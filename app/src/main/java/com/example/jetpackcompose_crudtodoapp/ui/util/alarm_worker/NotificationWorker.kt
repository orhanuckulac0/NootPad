package com.example.jetpackcompose_crudtodoapp.ui.util.alarm_worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class NotificationWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val notification  = MyNotification(applicationContext, "Notification Title", "Notification Body")
        notification.showNotification()

        return Result.success()
    }
}
