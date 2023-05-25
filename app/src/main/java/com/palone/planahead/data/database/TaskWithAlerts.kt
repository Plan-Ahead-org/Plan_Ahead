package com.palone.planahead.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task

data class TaskWithAlerts(
    @Embedded val task: Task,
    @Relation(parentColumn = "taskId", entityColumn = "taskId")
    val alerts: List<Alert>
)
