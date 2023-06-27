package com.palone.planahead.domain.taskEngine

import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.CoroutineScope

interface TaskEngine {
    val scope: CoroutineScope
    fun completeTask(task: Task)
}