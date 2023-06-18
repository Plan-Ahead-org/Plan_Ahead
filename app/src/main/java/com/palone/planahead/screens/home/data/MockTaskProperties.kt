package com.palone.planahead.screens.home.data

import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskPriority
import com.palone.planahead.data.database.task.properties.TaskType
import java.time.LocalDateTime

data class MockTaskProperties(
    val baseTask: Task = Task(null, TaskType.ONE_TIME, "", "", false, TaskPriority.LOW),
    val alertTriggers: List<AlertTrigger> = emptyList(),
    val alertTypes: List<AlertType> = emptyList(),
    val alertEventMillisInEpoch: Long? = null,
    val alertInterval: Long? = null,
    val alertSelectedMultipleTimes: List<LocalDateTime> = emptyList(),
)
