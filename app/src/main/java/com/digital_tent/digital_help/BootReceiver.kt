package com.digital_tent.digital_help

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            println("Запуск службы BootReceiver")
            val globalVariables = context?.applicationContext as GlobalVariables
            if (globalVariables.getAutoPing()) {
                globalVariables.setPing(true)
                val pingService = Intent(context, PingService::class.java)
                context.startForegroundService(pingService)
            }
        }
    }
}