package com.digital_tent.digital_help

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        // Переменные.
        val globalVariables: GlobalVariables = application as GlobalVariables
        val buttonCamera1: Button = findViewById(R.id.camera_button_1)
        val buttonCamera2: Button = findViewById(R.id.camera_button_2)
        val webViewCamera: WebView = findViewById(R.id.camera_web_view)
        // Выполнить подключение к камере.
        buttonCamera1.setOnClickListener {
            val url = "http://" + globalVariables.getCameraIp1()
            webViewCamera.loadUrl(url)
        }
        buttonCamera2.setOnClickListener {
            val url = "http://" + globalVariables.getCameraIp2()
            webViewCamera.loadUrl(url)
        }
    }
}