package com.digital_tent.digital_help

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SettingsActivity : AppCompatActivity() {
    private val globalVariables: GlobalVariables = application as GlobalVariables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Переменные.

        val terminalName: EditText = findViewById(R.id.settings_edit_text_terminal_name)
        val cameraIp1: EditText = findViewById(R.id.settings_edit_text_camera_1)
        val cameraIp2: EditText = findViewById(R.id.settings_edit_text_camera_2)
        val buttonSave: Button = findViewById(R.id.settings_button_save)

        buttonSave.setOnClickListener {
            saveSettings()
        }

        // Установить значения из глобального хранилища.
        terminalName.setText(globalVariables.getTerminalName())
        cameraIp1.setText(globalVariables.getCameraIp1())
        cameraIp2.setText(globalVariables.getCameraIp2())
    }

    private fun saveSettings() {
        val cameraIp1: EditText = findViewById(R.id.settings_edit_text_camera_1)
        val cameraIp2: EditText = findViewById(R.id.settings_edit_text_camera_2)
        globalVariables.setCameraIp1(cameraIp1.text.toString())
        globalVariables.setCameraIp1(cameraIp2.text.toString())
    }
}