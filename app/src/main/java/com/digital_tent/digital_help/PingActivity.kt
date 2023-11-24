package com.digital_tent.digital_help

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


class PingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ping)

        // Переменные.
        val globalVariables: GlobalVariables = application as GlobalVariables
        val buttonStart: Button = findViewById(R.id.ping_button_start)
        val buttonEnd: Button = findViewById(R.id.ping_button_end)
        var text: TextView = findViewById(R.id.ping_text)

        buttonStart.setOnClickListener {
            globalVariables.setPing(true)
            val pingService = Intent(this, PingService::class.java)
            startService(pingService)
        }
        buttonEnd.setOnClickListener {
            globalVariables.setPing(false)
            val pingService = Intent(this, PingService::class.java)
            stopService(pingService)
        }
    }
}