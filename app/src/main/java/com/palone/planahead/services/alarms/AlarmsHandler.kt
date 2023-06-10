package com.palone.planahead.services.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.palone.planahead.data.database.TaskWithAlerts
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskType
import java.time.LocalDateTime
import java.time.OffsetDateTime

class AlarmsHandler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    fun createAlarmEntries(tasks: List<TaskWithAlerts>) {
        tasks.forEach { (task, alerts) ->
            alerts.forEach { alert: Alert ->
                val currentTimeInMilliEpoch =
                    LocalDateTime.now().toEpochSecond(OffsetDateTime.now().offset) * 1000
                if ((alert.eventMillisInEpoch ?: 0) > currentTimeInMilliEpoch) {
                    addAlarm(alert, task)
                }
            }
        }
    }
    @SuppressLint("MissingPermission") // permissions are granted anyway
    fun addAlarm(alert: Alert, task: Task) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra("alert", alert)
        intent.putExtra("task", task)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alert.alertId ?: 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.i("New alarm found :)", alert.toString() + task.toString())
        when (task.taskType) {
            TaskType.ONE_TIME -> setOneTimeAlarm(alarmManager, alert, pendingIntent)
            TaskType.CHORE -> setChoreAlarm(alarmManager, alert, pendingIntent)
            TaskType.CRON -> setCronAlarm(alarmManager, alert, pendingIntent)
        }
    }
}

@SuppressLint("MissingPermission") // permissions are granted anyway
fun setOneTimeAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent) {
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        alert.eventMillisInEpoch ?: 0,
        pendingIntent
    )
}

@SuppressLint("MissingPermission") // permissions are granted anyway
fun setChoreAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent) {
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        alert.eventMillisInEpoch ?: 0,
        alert.interval ?: 0,
        pendingIntent
    )
}

@SuppressLint("MissingPermission") // permissions are granted anyway
fun setCronAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent) {
    // TODO impl later
}