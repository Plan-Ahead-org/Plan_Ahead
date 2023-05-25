package com.palone.planahead.services.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import java.time.LocalDateTime
import java.time.OffsetDateTime

class AlarmsHandler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    private var _tasks: Map<Task, List<Alert>> = emptyMap()
    fun inflateTasks(tasks: Map<Task, List<Alert>>) {
        _tasks = tasks
    }

    @SuppressLint("MissingPermission")
    fun createAlarmEntries() {
        _tasks.forEach { (task, alerts) ->
            alerts.forEach { alert: Alert ->
                val currentTimeInMilliEpoch =
                    LocalDateTime.now().toEpochSecond(OffsetDateTime.now().offset) * 1000
                if ((alert.alert_trigger_data ?: "0").toLong() > currentTimeInMilliEpoch) {
                    val intent = Intent(context, AlarmBroadcastReceiver::class.java)
                    intent.putExtra("alert", alert)
                    intent.putExtra("task", task)
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        alert.alert_id ?: 0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        alert.alert_trigger_data?.toLong() ?: 0,
                        pendingIntent
                    )
                    Log.i("Added :)", alert.toString())
                }
            }
        }
    }
}