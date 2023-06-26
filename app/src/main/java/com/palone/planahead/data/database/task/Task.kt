package com.palone.planahead.data.database.task

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.palone.planahead.data.database.task.properties.TaskPriority
import com.palone.planahead.data.database.task.properties.TaskSource
import com.palone.planahead.data.database.task.properties.TaskType
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = null,
    val taskType: TaskType,
    val description: String,
    val addedDate: String,
    val isCompleted: Boolean,
    val priority: TaskPriority = TaskPriority.LOW,
    val source: TaskSource = TaskSource.USER
) : Parcelable
