package com.digital_tent.digital_help

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


private const val CHANNEL_ID = "PingChannel"
private const val NOTIFICATION_ID = 1

class PingService: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var responseCounter: Int = 0
        var upDateTime: Long = 0
        var upDateTimeString: String = ""
        var shotDownTime: Long = 0
        var shotDownTimeString: String = ""
        val globalVariables = application as GlobalVariables
        globalVariables.setPing(true)

        createNotificationChannel(application)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Проверка ОКТО")
            .setContentText("Идёт проверка..")
            .setSmallIcon(R.drawable.ping_on)
        val notificationPing = notificationBuilder.build()

        startForeground(NOTIFICATION_ID, notificationPing)
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                application,
                "Проверка запущена", Toast.LENGTH_SHORT
            ).show()
        }
        CoroutineScope(Dispatchers.IO).launch {
            while (globalVariables.getPing()) {
                delay(1000)
                try {
                    if (getRequest() == 200 && responseCounter >= 30) {
                        // Отправка сообщения об ошибке.
                        upDateTime = LocalDateTime.now()
                            .atZone(ZoneId.of("Europe/Moscow"))
                            .toInstant().toEpochMilli() / 1000
                        upDateTimeString = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("hh:mm:ss"))
                        val totalTimeError = upDateTime - shotDownTime
                        sendTelegramMessage(
                            shotDownDateTime = shotDownTimeString,
                            upDateTime = upDateTimeString,
                            totalTimeError = totalTimeError.toInt()
                        )
                        responseCounter = 0
                        createNotification(application, "Проверка ОКТО", "Идёт проверка..",
                            R.drawable.ping_on)
                    } else if (getRequest() == 200 && 29 >= responseCounter && responseCounter >= 1) {
                        createNotification(application, "Проверка ОКТО", "Идёт проверка..",
                            R.drawable.ping_on)
                        responseCounter = 0
                    }
                } catch (e: IOException) {
                    if (responseCounter == 0) {
                        createNotification(application, "Проверка ОКТО", "Возникла ошибка",
                            R.drawable.ping_off)
                        shotDownTimeString = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("hh:mm:ss"))
                        shotDownTime = LocalDateTime.now()
                            .atZone(ZoneId.of("Europe/Moscow"))
                            .toInstant().toEpochMilli() / 1000
                    }
                    responseCounter += 1
                }
            }
            createNotification(application, "Проверка завершена", "Всё хорошо",
                R.drawable.ping_on)
        }
        return START_STICKY
    }

    private fun getRequest(): Int {
        var code: Int
        val client = okhttp3.OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        val request = okhttp3.Request.Builder()
            .url("http://app.okto.ru/users/sign_in")
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException(
                    "Запрос к серверу провален:" +
                            "${response.code} ${response.message}"
                )
            }
            code = response.code
            return code
        }
    }

    private fun sendTelegramMessage(
        shotDownDateTime: String, upDateTime: String,
        totalTimeError: Int
    ) {
        val globalVariables: GlobalVariables = application as GlobalVariables
        val idBot = globalVariables.getIdBot()
        val idChat = globalVariables.getIdChat()
        val terminalName = globalVariables.getTerminalName()
        val bot = TelegramBot(idBot)
        bot.execute(
            SendMessage(
                idChat, LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        "\nТерминал: $terminalName\n" +
                        "Ошибка: Красный экран\n" +
                        "$shotDownDateTime - OFF\n" +
                        "$upDateTime - ON\n" +
                        "$totalTimeError - LOST sec."
            )
        )
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "ChannelPing",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(context: Context, titleOKTO: String, textOKTO: String,
                                   imgOKTO: Int) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(titleOKTO)
            .setContentText(textOKTO)
            .setSmallIcon(imgOKTO)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                application,
                "Пинг остановлен", Toast.LENGTH_SHORT
            ).show()
        }
    }
}