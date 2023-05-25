package com.palone.planahead.data.database.alert

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task

@Entity(
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = arrayOf("task_id"),
        childColumns = arrayOf("task_id"),
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Alert(
    @PrimaryKey(autoGenerate = true)
    val alert_id: Int? = null,
    val task_id: Int? = null,
    val alert_type_name: AlertType,
    val alert_trigger_name: AlertTrigger,
    val alert_trigger_data: String? = null
)
