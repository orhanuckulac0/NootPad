package com.example.jetpackcompose_crudtodoapp.ui.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.i("MYTAG", "BOOT COMPLETED")

            val serviceIntent = Intent(context, DataRetrievalService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}
