package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskType
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun TaskItem(modifier: Modifier = Modifier, task: Task, alerts: List<Alert>) {
    val alertTypes = remember { mutableStateListOf<AlertType>() }
    val alertTriggers = remember { mutableStateListOf<AlertTrigger>() }
    SideEffect {
        alerts.forEach { alert ->
            if (!alertTriggers.contains(alert.alertTriggerName)) alertTriggers.add(alert.alertTriggerName)
            if (!alertTypes.contains(alert.alertTypeName)) alertTypes.add(alert.alertTypeName)
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.BrokenImage,
                "Image of selected icon",
                modifier = Modifier.size(50.dp)
            )
            Column {
                Text(text = task.description)
                alertTypes.forEach { alertType ->
                    Text(text = alertType.name, fontSize = 5.sp)
                }
            }
        }
        Text(text = task.taskType.name)
        Column {
            alerts.forEach {
                val date = it.eventMillisInEpoch?.let { it1 -> Date(it1) }
                val pattern = "yyyy-MM-dd HH:mm:ss"
                val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
                val formattedDate = date?.let { it1 -> simpleDateFormat.format(it1) }
                if (formattedDate != null) {
                    Text(text = formattedDate)
                }

            }
        }
        Icon(Icons.Default.ArrowRight, "Trailing Icon")
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = Task(
            1, TaskType.ONE_TIME, "task description wew such long one", LocalDateTime.now().format(
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            ), isCompleted = false
        ), alerts = listOf(
            Alert(1, 1, AlertType.PERSISTENT_NOTIFICATION, AlertTrigger.TIME, 1684973640000, null),
            Alert(1, 1, AlertType.NOTIFICATION, AlertTrigger.TIME, 1684973640000, null),
            Alert(1, 1, AlertType.ALARM, AlertTrigger.LOCATION, 1684973640000, null)
        )
    )
}