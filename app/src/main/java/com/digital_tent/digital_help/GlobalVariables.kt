package com.digital_tent.digital_help

import android.app.Application
import android.content.SharedPreferences
import java.util.prefs.AbstractPreferences

class GlobalVariables: Application() {
    // Постоянное хранилище данных.
    private lateinit var sharedPreferences: SharedPreferences
    // Доступ для изменений в хранилище данных.
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate() {
        super.onCreate()
        // Доступ только у приложения к хранилищу данных.
        sharedPreferences = getSharedPreferences("global_variables", MODE_PRIVATE)
        // Инициализация доступа для изменений.
        editor = sharedPreferences.edit()
    }


    // Получить имя терминала.
    fun getTerminalName(): String {
        return sharedPreferences.getString("terminal_name", "")?: ""
    }
    // Сохранить имя терминала.
    fun setTerminalName(terminalName: String) {
        editor.putString("terminal_name", terminalName)
        editor.apply()
    }
    // Получить ip-адрес камеры №1.
    fun getCameraIp1(): String {
        return sharedPreferences.getString("camera_ip1", "")?: ""
    }
    // Сохранить ip-адрес камеры №1.
    fun setCameraIp1(cameraIp1: String) {
        editor.putString("camera_ip1", cameraIp1)
        editor.apply()
    }
    // Получить ip-адрес камеры №2.
    fun getCameraIp2(): String {
        return sharedPreferences.getString("camera_ip2", "")?: ""
    }
    // Сохранить ip-адрес камеры №2.
    fun setCameraIp2(cameraIp2: String) {
        editor.putString("camera_ip2", cameraIp2)
        editor.apply()
    }
    // Получить флаг на авто-пинг.
    fun getAutoPing(): Boolean {
        return sharedPreferences.getBoolean("auto_ping", false)
    }
    // Сохранить флаг на авто-пинг.
    fun setAutoPing(autoPing: Boolean) {
        editor.putBoolean("auto_ping", autoPing)
        editor.apply()
    }
    // Получить параметр количества кодов в файле.
    fun getQuantityInFile(): Int {
        return sharedPreferences.getInt("quantity_in_file", 200000)
    }
    // Сохранить параметр количества кодов в файле.
    fun setQuantityInFile(quantityInFile: Int) {
        editor.putInt("quantity_in_file", quantityInFile)
        editor.apply()
    }
    // Получить статус пинга.
    fun getPing(): Boolean {
        return sharedPreferences.getBoolean("ping", false)
    }
    // Сохранить статус пинга.
    fun setPing(ping: Boolean) {
        editor.putBoolean("ping", ping)
        editor.apply()
    }
    // Получить id бота.
    fun getIdBot(): String {
        return sharedPreferences.getString("bot_id",
            "")?: ""
    }
    // Получить id чата.
    fun getIdChat(): String {
        return sharedPreferences.getString("chat_id", "")?: ""
    }
}