package com.palone.planahead.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskAndAlertDao {
    @Upsert
    suspend fun upsertTask(task: Task): Long

    @Upsert
    suspend fun upsertAlert(alert: Alert): Long

    @Delete
    suspend fun deleteTask(task: Task)

    @Delete
    suspend fun deleteAlert(alert: Alert)

    @Query(
        "SELECT * FROM alert " +
                "JOIN task ON alert.task_id = task.task_id "
    )
    fun getAllTasksAndAlerts(): Flow<Map<Task, List<Alert>>>

}