package com.digital_tent.digital_help

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class CameraActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        // Переменные.
        val globalVariables: GlobalVariables = application as GlobalVariables
        val buttonCamera1: Button = findViewById(R.id.camera_button_1)
        val buttonCamera2: Button = findViewById(R.id.camera_button_2)
        val webViewCamera: WebView = findViewById(R.id.camera_web_view)
//        val cameraScrollView: ScrollView = findViewById(R.id.camera_scroll_view)
        // Выполнить подключение к камере.
        buttonCamera1.setOnClickListener {
            val url = "http://" + globalVariables.getCameraIp1() + "/monitor"
            webViewCamera.loadUrl(url)
            webViewCamera.settings.javaScriptEnabled = true
            webViewCamera.settings.loadsImagesAutomatically = true
//            cameraScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
        buttonCamera2.setOnClickListener {
            val url = "http://" + globalVariables.getCameraIp2() + "/monitor"
            webViewCamera.loadUrl(url)
            webViewCamera.settings.javaScriptEnabled = true
            webViewCamera.settings.loadsImagesAutomatically = true
        }
    }
}