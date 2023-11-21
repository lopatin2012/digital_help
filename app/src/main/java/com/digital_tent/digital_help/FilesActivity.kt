package com.digital_tent.digital_help

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class FilesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)
        // Добавить отображение статуса загрузки файлов.

        // Переменные.
        val globalVariables: GlobalVariables = application as GlobalVariables
        val buttonUpload: Button = findViewById(R.id.files_button_upload)
        val buttonDelete: Button = findViewById(R.id.files_button_delete)
        var text: TextView = findViewById(R.id.files_text)
    }
}