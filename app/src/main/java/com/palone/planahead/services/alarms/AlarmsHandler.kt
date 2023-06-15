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

class AlarmsHandler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    fun createAlarmEntries(tasks: List<TaskWithAlerts>) {
        tasks.forEach { (task, alerts) ->
            alerts.forEach { alert: Alert ->
                val shouldShowBackwardsToo =
                    (task.taskType != TaskType.ONE_TIME) && !task.isCompleted
                if (shouldShowBackwardsToo) {
                    addAlarm(alert, task)
                }
            }
        }
    }

    private fun addAlarm(alert: Alert, task: Task) {
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
            TaskType.CHORE -> setChoreAlarm(alarmManager, alert, pendingIntent)
            TaskType.CRON -> setCronAlarm(alarmManager, alert, pendingIntent)
        }
    }
}

fun setOneTimeAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent) {
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        alert.eventMillisInEpoch ?: 0,
        pendingIntent
    )
}

fun setChoreAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent) {
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        alert.eventMillisInEpoch ?: 0,
        alert.interval ?: 0,
        pendingIntent
    )
}

fun setCronAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent) {
    // TODO impl later (do I need it?)
}