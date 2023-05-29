package com.palone.planahead.data.database.alert

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task
import kotlinx.parcelize.Parcelize

@Entity(
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = arrayOf("taskId"),
        childColumns = arrayOf("taskId"),
        onDelete = ForeignKey.SET_NULL
    )]
)
@Parcelize
data class Alert(
    @PrimaryKey(autoGenerate = true)
    val alertId: Int? = null,
    val taskId: Int? = null,
    val alertTypeName: AlertType,
    val alertTriggerName: AlertTrigger,
    val alertTriggerData: String? = null
) : Parcelable
