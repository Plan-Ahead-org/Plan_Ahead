package com.palone.planahead.services.alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.planahead.R
import com.palone.planahead.AlarmScreenActivity
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task

class AlarmBroadcastReceiver : BroadcastReceiver() {
    private val channelId = "NotificationChannel"
    override fun onReceive(context: Context?, intent: Intent?) {
        val alert = intent?.getParcelableExtra<Alert>("alert")
        val task = intent?.getParcelableExtra<Task>("task")
        setAlarms(context, alert, task)
        if (alert != null) {
            if (alert.alertTypeName == AlertType.ALARM)
                showScreenUi(context, alert, task)
        }
    }

    private fun showScreenUi(context: Context?, alert: Alert?, task: Task?) {
        val alarmScreenIntent = Intent(context, AlarmScreenActivity::class.java)
        alarmScreenIntent.putExtra("alert", alert)
        alarmScreenIntent.putExtra("task", task)
        alarmScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(alarmScreenIntent)
    }

    private fun setAlarms(context: Context?, alert: Alert?, task: Task?) {
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
                alert.alertId!!,
                if (alert.alertTypeName == AlertType.PERSISTENT_NOTIFICATION)
                    persistentNotification
                else singleNotification
            )
        }
    }
}