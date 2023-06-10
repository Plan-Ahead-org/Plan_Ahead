package com.palone.planahead.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao : com.palone.planahead.data.database.Dao<Task> {
    @Transaction
    @Query("SELECT * FROM task ")
    fun getTasksWithAlerts(): Flow<List<TaskWithAlerts>>
}