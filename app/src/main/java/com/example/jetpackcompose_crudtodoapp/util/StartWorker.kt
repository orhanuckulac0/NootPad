package com.example.jetpackcompose_crudtodoapp.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun startWorker(context: Context, isNotified: Boolean) {
    if (!isNotified) {
        val workRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context.applicationContext)
            .enqueueUniquePeriodicWork(
                "notificationWork",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}
