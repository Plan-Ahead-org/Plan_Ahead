package com.palone.planahead.data.database.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val task_id: Int? = null,
    val description: String,
    val addedDate: String
)
