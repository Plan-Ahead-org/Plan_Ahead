package com.example.planahead.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.planahead.data.task.Task
import java.time.LocalDateTime
import java.time.LocalTime

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
        newTask:Task
    ) {
        task = newTask
    }
    fun getTask():Task{
        return task
    }
    suspend fun clearTask(){
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