package com.palone.planahead.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsert(task: Task): Long

    @Delete
    suspend fun delete(task: Task)

    @Transaction
    @Query("SELECT * FROM task ")
    fun getTasksWithAlerts(): Flow<List<TaskWithAlerts>>
}