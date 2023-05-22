package com.example.planahead.data.database

import com.example.planahead.data.task.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    suspend fun upsert(task: Task){
        taskDao.upsertTask(task)
    }
    suspend fun delete(task: Task){
        taskDao.deleteTask(task)
    }
}