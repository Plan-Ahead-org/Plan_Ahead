package com.palone.planahead.data.database

import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasksWithAlerts: Flow<List<TaskWithAlerts>> = taskDao.getTasksWithAlerts()
    suspend fun upsert(task: Task): Long {
        return taskDao.upsert(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
}