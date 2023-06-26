package com.palone.planahead.services

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.domain.taskEngine.TaskEngine
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MarkAsDoneService : Service() {
    @Inject
    lateinit var taskEngine: TaskEngine

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val task = intent?.getParcelableExtra<Task>("task")
        val alert = intent?.getParcelableExtra<Alert>("alert")

        Log.i("MarkAsDoneActivity", "called... :), $task")

        if (alert != null) {
            hideNotification(this, alert)
        }

        if (task != null) {
            taskEngine.completeTask(task)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun hideNotification(
        context: Context,
        alert: Alert
    ) { // hides notification from the notification shade/panel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (alert.alertId != null) {
            notificationManager.cancel(alert.alertId)
        }
    }

}