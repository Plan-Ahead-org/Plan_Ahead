package com.palone.planahead.services.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import com.palone.planahead.data.database.TaskWithAlerts
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task

interface AlarmsHandler {
    fun setAlarms(tasks: List<TaskWithAlerts>)
    fun disableAlarm(taskWithAlerts: TaskWithAlerts)
    fun setAlarm(alert: Alert, task: Task)
    fun setOneTimeAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent)
    fun setRepeatableAlarm(alarmManager: AlarmManager, alert: Alert, pendingIntent: PendingIntent)
}