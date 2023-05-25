package com.palone.planahead.services.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.palone.planahead.data.database.TaskWithAlerts
import com.palone.planahead.data.database.alert.Alert
import java.time.LocalDateTime
import java.time.OffsetDateTime

class AlarmsHandler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    private var _tasks: List<TaskWithAlerts> = emptyList()
    fun inflateTasks(tasks: List<TaskWithAlerts>) {
        _tasks = tasks
    }

    @SuppressLint("MissingPermission")
    fun createAlarmEntries() {
        _tasks.forEach { (task, alerts) ->
            alerts.forEach { alert: Alert ->
                val currentTimeInMilliEpoch =
                    LocalDateTime.now().toEpochSecond(OffsetDateTime.now().offset) * 1000
                if ((alert.alertTriggerData ?: "0").toLong() > currentTimeInMilliEpoch) {
                    val intent = Intent(context, AlarmBroadcastReceiver::class.java)
                    intent.putExtra("alert", alert)
                    intent.putExtra("task", task)
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        alert.alertId ?: 0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        alert.alertTriggerData?.toLong() ?: 0,
                        pendingIntent
                    )
                    Log.i("Added :)", alert.toString())
                }
            }
        }
    }
}