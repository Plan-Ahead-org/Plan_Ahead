package com.palone.planahead.services.alarms

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.PopupWindow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
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
            showPopupWindow(context)
        }
    }

    private fun showScreenUi(context: Context?, alert: Alert?, task: Task?) {
        val alarmScreenIntent = Intent(context, AlarmScreenActivity::class.java)
        alarmScreenIntent.putExtra("alert", alert)
        alarmScreenIntent.putExtra("task", task)
        alarmScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(alarmScreenIntent)
    }

    private fun showPopupWindow(context: Context?) {
        val popupWindow = PopupWindow(context)
        val rootView = (context as Activity).window.decorView.rootView
        popupWindow.contentView = ComposeView(context).apply {
            setContent {
                Card {
                    Text(text = "That's a test ma boi", modifier = Modifier.padding(10.dp))
                }
            }
        }
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0)
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

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(task?.description)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Nie zrobiłeś jeszcze tej rzeczy!")
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (alert != null) {
            if (alert.alertTypeName == AlertType.PERSISTENT_NOTIFICATION)
                notification.setOngoing(true)
            notificationManager.notify(
                alert.alertId!!,
                notification.build()
            )
        }
    }
}