package com.palone.planahead.data.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val description: String,
    val taskType: String,
    val alertTypes: Int,
    val alertTrigger: String,
    val alertTimeTrigger: String? = null, //depends on the alert trigger selection
    val alertLocationTrigger: String? = null, //idk the type of that yet, depends on the alert trigger selection
    val addedDate: String
)
