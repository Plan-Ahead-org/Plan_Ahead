package com.palone.planahead.domain.alertEngine

import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.CoroutineScope

interface AlertEngine {
    val scope: CoroutineScope
    fun createDelayedOneTimeAlert(
        alert: Alert,
        task: Task,
        delay: Long
    )
}