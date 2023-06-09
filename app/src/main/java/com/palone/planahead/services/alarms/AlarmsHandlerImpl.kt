package com.palone.planahead.services.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.palone.planahead.data.database.TaskWithAlerts
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskType

class AlarmsHandlerImpl(
    private val context: Context,
    private val alarmManager: AlarmManager
) : AlarmsHandler {
    override fun setAlarms(tasks: List<TaskWithAlerts>) {
        tasks.forEach { taskWithAlerts: TaskWithAlerts ->
            if (taskWithAlerts.task.isCompleted) {
                disableAlarm(taskWithAlerts)
            } else {
                taskWithAlerts.alerts.forEach { alert: Alert ->
                    val isOneTimeFromThePast =
                        (System.currentTimeMillis() > (alert.eventMillisInEpoch
                            ?: 0) && taskWithAlerts.task.taskType == TaskType.ONE_TIME)
                    if (!isOneTimeFromThePast) {
                        setAlarm(alert, taskWithAlerts.task)
                    }
                }
            }
        }
    }


    override fun disableAlarm(taskWithAlerts: TaskWithAlerts) {
        taskWithAlerts.alerts.forEach { alert ->
            if (alert.alertId != null) {
                val intent = Intent(context, AlarmBroadcastReceiver::class.java)
                intent.putExtra("alert", alert)
                intent.putExtra("task", taskWithAlerts.task)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    alert.alertId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    override fun setAlarm(alert: Alert, task: Task) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra("alert", alert)
        intent.putExtra("task", task)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alert.alertId ?: 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.i(
            "New alarm found :)",
            alert.toString() + task.toString()
        ) // will leave it until I'm done with this part of code
        when (task.taskType) {
            TaskType.ONE_TIME -> setOneTimeAlarm(alarmManager, alert, pendingIntent)
            TaskType.CHORE -> setRepeatableAlarm(alarmManager, alert, pendingIntent)
            TaskType.CRON -> setRepeatableAlarm(alarmManager, alert, pendingIntent)
        }
    }

    override fun setOneTimeAlarm(
        alarmManager: AlarmManager,
        alert: Alert,
        pendingIntent: PendingIntent
    ) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alert.eventMillisInEpoch ?: 0,
            pendingIntent
        )
    }

    override fun setRepeatableAlarm(
        alarmManager: AlarmManager,
        alert: Alert,
        pendingIntent: PendingIntent
    ) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alert.eventMillisInEpoch ?: 0,
            alert.interval ?: 0,
            pendingIntent
        )
    }
}

