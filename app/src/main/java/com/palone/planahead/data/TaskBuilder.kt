package com.palone.planahead.data

import com.palone.planahead.data.task.Task
import java.time.LocalDateTime

class TaskBuilder {
    private var task = Task(
        null,
        "",
        "",
        9,
        "",
        "",
        null,
        addedDate = LocalDateTime.now().toString()
    )

    fun updateTask(
        newTask: Task
    ) {
        task = newTask
    }

    fun getTask(): Task {
        return task
    }

    suspend fun clearTask() {
        task = Task(
            null,
            "",
            "",
            9,
            "",
            "",
            null,
            addedDate = LocalDateTime.now().toString()
        )
    }
}