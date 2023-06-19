package com.palone.planahead.services.alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.palone.planahead.AlarmScreenActivity
import com.palone.planahead.MarkAsDoneService
import com.palone.planahead.R
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task

class AlarmBroadcastReceiver : BroadcastReceiver() {
    private val channelId = "NotificationChannel"
    override fun onReceive(context: Context?, intent: Intent?) {
        val alert =
            intent?.getParcelableExtra<Alert>("alert") // deprecated, but new version needs android 13, so using it is pointless
        val task = intent?.getParcelableExtra<Task>("task") // ^^^
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

        val markAsDoneIntent = Intent(context, MarkAsDoneService::class.java)
        markAsDoneIntent.putExtra("task", task)
        markAsDoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        markAsDoneIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)


        val markAsDonePendingIntent = PendingIntent.getService(
            context,
            alert?.alertId ?: 0,
            markAsDoneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(task?.description)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("You haven't done that yet!")
            .addAction(R.drawable.ic_launcher_foreground, "Done", markAsDonePendingIntent)

        if (alert != null) {
            if (alert.alertTypeName == AlertType.PERSISTENT_NOTIFICATION) {
                notification.setOngoing(true)
            }
        }


        if (alert != null) {
            notificationManager.notify(
                alert.alertId!!,
                notification.build()
            )
        }
        Log.i("Received Alarm :)", alert.toString())
    }
}