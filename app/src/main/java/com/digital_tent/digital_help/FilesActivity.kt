package com.digital_tent.digital_help

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.pengrad.telegrambot.request.SendDocument
import java.io.Writer
import java.nio.file.Files

class FilesActivity : AppCompatActivity() {
    private val permissionWriteInStorage = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)
        // Добавить отображение статуса загрузки файлов.

        // Переменные.
        val globalVariables: GlobalVariables = application as GlobalVariables
        val buttonUpload: Button = findViewById(R.id.files_button_upload)
        val buttonDelete: Button = findViewById(R.id.files_button_delete)
        var text: TextView = findViewById(R.id.files_text)

        // Отправка файлов по нажатию на кнопку.
        buttonUpload.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Если разрешение не было предоставлено, запрашиваем его у пользователя
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    permissionWriteInStorage
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    permissionWriteInStorage
                )
                uploadFiles()
            }
        }
        // Удаление файлов по нажатию на кнопку.
        buttonDelete.setOnClickListener {
            deleteFiles()
        }
    }

    // Отправка файлов.
    private fun uploadFiles() {
        val listOfFiles: List<String> = listOf(
            "duplicates", "videojet_codes"
        )
        val globalVariables: GlobalVariables = application as GlobalVariables
        val bot = TelegramBot(globalVariables.getIdBot())
        CoroutineScope(Dispatchers.IO).launch {
            bot.execute(
                SendMessage(
                    globalVariables.getIdChat(), "Отправка файлов с терминала: "
                            + globalVariables.getTerminalName()
                )
            )
            if (ContextCompat.checkSelfPermission(
                    this@FilesActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                for (fileName in listOfFiles) {
                    fileCompression(fileName)
                    val filePath = File("/storage/emulated/0/temp_$fileName.txt")
                    if (filePath.isFile) {
                        bot.execute(SendDocument(globalVariables.getIdChat(), filePath))
                    }
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        this@FilesActivity,
                        "Необходим доступ к файлам\nДоступ запрещён",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                requestWritePermission()
            }
        }
    }

    // Сжатие файлов до желаемого размера строк.
    private fun fileCompression(fileName: String) {
        val sourceFile = File("/storage/emulated/0/$fileName.txt")
        val globalVariables: GlobalVariables = application as GlobalVariables
        val quantityInFile = globalVariables.getQuantityInFile()
        val targetFile = File("/storage/emulated/0/temp_" + sourceFile.name)

        try {
            val listOfCodes = sourceFile.bufferedReader().readLines()

            val subList = if (listOfCodes.size > quantityInFile) {
                listOfCodes.subList(listOfCodes.size - quantityInFile, listOfCodes.size)
            } else {
                listOfCodes
            }

            targetFile.bufferedWriter().use { writer ->
                writer.write(subList.joinToString("\n"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Проверка доступа на запись/чтение файлов.
    private fun requestWritePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                permissionWriteInStorage
            )
        } else {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }

    // Удаление файлов
    private fun deleteFiles() {
        val listOfFiles: List<String> = listOf(
            "duplicates", "videojet_codes", "temp_duplicates", "temp_videojet_codes",
            "queries", "server_codes", "videojet_requests"
        )

        CoroutineScope(Dispatchers.IO).launch {
            for (fileName in listOfFiles) {
                val targetFile = File("/storage/emulated/0/$fileName.txt")
                targetFile.delete()
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                this@FilesActivity,
                "Файлы удалены", Toast.LENGTH_SHORT
            ).show()
        }
    }
}