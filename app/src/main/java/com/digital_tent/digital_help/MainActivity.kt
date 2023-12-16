package com.digital_tent.digital_help

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private val permissionWriteInStorage = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Запуск проверки доступа для взаимодействия с файлами.
        requestWritePermission()
    }

    // Запустить активити с проверкой доступа.
    fun goToPingTab(view: View) {
        val intent = Intent(this@MainActivity, PingActivity::class.java)
        startActivity(intent)
    }

    // Запустить активити с файлами.
    fun goToFilesTab(view: View) {
        val intent = Intent(this@MainActivity, FilesActivity::class.java)
        startActivity(intent)
    }

    // Запустить активити с настройками приложения.
    fun goToSettingsTab(view: View) {
        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
        startActivity(intent)
    }

    // Запустить активити с камерами.
    fun goToCameraTab(view: View) {
        val intent = Intent(this@MainActivity, CameraActivity::class.java)
        startActivity(intent)
    }

    // Запустить активити с qr-кодом инструкции.
    fun goToQRInstruction(view: View) {
        val intent = Intent(this@MainActivity, InstructionsActivity::class.java)
        startActivity(intent)
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
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    permissionWriteInStorage
                )
            }
        }
    }
}