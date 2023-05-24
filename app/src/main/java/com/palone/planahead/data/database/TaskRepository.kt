package com.palone.planahead.data.database

import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskAndAlertDao: TaskAndAlertDao) {
    val allTasks: Flow<Map<Task, List<Alert>>> = taskAndAlertDao.getAllTasksAndAlerts()
    suspend fun upsertTask(task: Task): Long {
        return taskAndAlertDao.upsertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskAndAlertDao.deleteTask(task)
    }

    suspend fun deleteAlert(alert: Alert) {
        taskAndAlertDao.deleteAlert(alert)
    }

    suspend fun upsertAlert(alert: Alert): Long {
        return taskAndAlertDao.upsertAlert(alert)
    }
}