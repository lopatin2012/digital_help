package com.digital_tent.digital_help

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

}