package com.digital_tent.digital_help

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        // Переменные для установки значений из глобальных переменных.
        val terminalName: EditText = findViewById(R.id.settings_edit_text_terminal_name)
        val cameraIp1: EditText = findViewById(R.id.settings_edit_text_camera_1)
        val cameraIp2: EditText = findViewById(R.id.settings_edit_text_camera_2)
        val autoOrder: CheckBox = findViewById(R.id.settings_auto_ping_flag)
        val quantityInFile: EditText = findViewById(R.id.settings_edit_text_quantity_in_file)
        // Глобальные переменные.
        val globalVariables: GlobalVariables = application as GlobalVariables

        // Привязка кнопки для выполнения функции.
        val buttonSave: Button = findViewById(R.id.settings_button_save)
        buttonSave.setOnClickListener {
            saveSettings()
        }

        // Загрузка настроек.
        terminalName.setText(globalVariables.getTerminalName())
        cameraIp1.setText(globalVariables.getCameraIp1())
        cameraIp2.setText(globalVariables.getCameraIp2())
        autoOrder.isChecked = (globalVariables.getAutoPing())
        quantityInFile.setText(globalVariables.getQuantityInFile().toString())
    }

    // Сохранение настроек.
    private fun saveSettings() {
        // Глобальные переменные.
        val globalVariables: GlobalVariables = application as GlobalVariables
        // Камера №1.
        val cameraIp1: EditText = findViewById(R.id.settings_edit_text_camera_1)
        globalVariables.setCameraIp1(cameraIp1.text.toString())
        // Камера №2.
        val cameraIp2: EditText = findViewById(R.id.settings_edit_text_camera_2)
        globalVariables.setCameraIp2(cameraIp2.text.toString())
        // Имя терминала.
        val terminalName: EditText = findViewById(R.id.settings_edit_text_terminal_name)
        globalVariables.setTerminalName(terminalName.text.toString())
        // Режим авто-пинг.
        val autoOrder: CheckBox = findViewById(R.id.settings_auto_ping_flag)
        globalVariables.setAutoPing(autoOrder.isChecked)
        // Количество кодов в файле.
        val quantityInFile: EditText = findViewById(R.id.settings_edit_text_quantity_in_file)
        globalVariables.setQuantityInFile(quantityInFile.text.toString().toInt())
        // Создание уведомления о сохранении настроек.
        Toast.makeText(this@SettingsActivity,
            "Настройки сохранены", Toast.LENGTH_SHORT).show()
    }
}