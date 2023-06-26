package com.palone.planahead.domain.alertEngine

import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskSource
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.services.alarms.AlarmsHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class AlertEngine(
    private val taskRepository: TaskRepository,
    private val alarmsHandler: AlarmsHandler,
    private val alertRepository: AlertRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    fun createDelayedOneTimeAlert(
        alert: Alert,
        task: Task,
        delay: Long
    ) {
        scope.launch {
            val currentEventMillisInEpoch = alert.eventMillisInEpoch ?: 0
            val newTask =
                task.copy(taskId = null, taskType = TaskType.ONE_TIME, source = TaskSource.SYSTEM)
            val newTaskId = taskRepository.upsert(newTask)
            val newAlert = alert.copy(
                alertId = null,
                taskId = newTaskId.toInt(),
                eventMillisInEpoch = currentEventMillisInEpoch + delay
            )
            alertRepository.upsert(newAlert)
            alarmsHandler.setAlarms(taskRepository.allTasksWithAlerts.last()) // load changes
        }
    }
}