package com.digital_tent.digital_help

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ping)

        // Переменные.
        val buttonStart: Button = findViewById(R.id.ping_button_start)
        val buttonEnd: Button = findViewById(R.id.ping_button_end)
        var text: TextView = findViewById(R.id.ping_text)
    }
}