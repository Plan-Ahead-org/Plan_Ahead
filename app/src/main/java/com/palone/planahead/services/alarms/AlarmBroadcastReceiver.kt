package com.palone.planahead.services.alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.planahead.R
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task

class AlarmBroadcastReceiver : BroadcastReceiver() {
    val channelId = "NotificationChannel"
    override fun onReceive(context: Context?, intent: Intent?) {
        val alert = intent?.getParcelableExtra<Alert>("alert")
        val task = intent?.getParcelableExtra<Task>("task")
        if (intent == null) return
        val channel = NotificationChannel(
            channelId,
            "Notification channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val singleNotification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(task?.description)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Nie zrobiłeś jeszcze tej rzeczy!").build()
        val persistentNotification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(task?.description)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setContentText("Nie zrobiłeś jeszcze tej rzeczy!").build()

        if (alert != null) {
            notificationManager.notify(
                alert.alert_id!!,
                if (alert.alert_type_name == AlertType.PERSISTENT_NOTIFICATION)
                    persistentNotification
                else singleNotification
            )
        }


    }

}