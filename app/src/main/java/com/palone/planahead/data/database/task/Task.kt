package com.palone.planahead.data.database.task

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.palone.planahead.data.database.task.properties.TaskPriority
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val task_id: Int? = null,
    val description: String,
    val addedDate: String,
    val isCompleted: Boolean,
    val priority: TaskPriority = TaskPriority.LOW
) : Parcelable
