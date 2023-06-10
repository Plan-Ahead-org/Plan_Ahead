package com.palone.planahead.data.database

import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(taskDao: TaskDao) : Repository<Task>(taskDao) {
    val allTasksWithAlerts: Flow<List<TaskWithAlerts>> = taskDao.getTasksWithAlerts()
}