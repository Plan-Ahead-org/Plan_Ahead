package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    alerts: List<Alert>,
    onDelete: (Task) -> Unit = {}
) {
    val alertTypes = remember { mutableStateListOf<AlertType>() }
    val alertTriggers = remember { mutableStateListOf<AlertTrigger>() }
    val shouldExpand = remember {
        mutableStateOf(false)
    }
    val lastNotification = alerts.sortedBy { it.interval }.last()
    val lastNotificationDate = lastNotification.eventMillisInEpoch?.let { it1 -> Date(it1) }
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    val formattedLastNotificationDate =
        lastNotificationDate?.let { it1 -> simpleDateFormat.format(it1) } // Is it ok doing it here?
    SideEffect {
        alerts.forEach { alert ->
            if (!alertTriggers.contains(alert.alertTriggerName)) alertTriggers.add(alert.alertTriggerName)
            if (!alertTypes.contains(alert.alertTypeName)) alertTypes.add(alert.alertTypeName)
        }
    }
    Card {
        Row(
            modifier = modifier.clickable { shouldExpand.value = !shouldExpand.value },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = task.description)
                Row {
                    Text(text = "Type: ${task.taskType}")
                }
                if (formattedLastNotificationDate != null) {
                    if (task.taskType == TaskType.ONE_TIME) {
                        Text(text = "Last notification at $formattedLastNotificationDate")
                    }
                }
                if (shouldExpand.value) {
                    ExpandedItem(task = task, alerts = alerts, onDelete = onDelete)
                }
            }
            if (shouldExpand.value) {
                Icon(Icons.Default.ArrowDropDown, "Trailing Icon")
            } else {
                Icon(Icons.Default.ArrowRight, "Trailing Icon")
            }
        }
    }
}

@Composable
fun ExpandedItem(
    task: Task,
    alerts: List<Alert>, onDelete: (Task) -> Unit
) {
    Icon(tint = Color.Red,
        imageVector = Icons.Default.Delete,
        contentDescription = "Delete this reminder",
        modifier = Modifier
            .clickable { onDelete(task) }
            .size(40.dp)
    )
    Column {
        alerts.forEach {
            Column {
                Text(text = "ID: ${it.alertId}")
                Text(text = "Type: ${it.alertTypeName}")
                Text(text = "Millis In Epoch: ${it.eventMillisInEpoch}")
                Text(text = "Interval: ${it.interval}")
                Text(text = "isCompleted: ${task.isCompleted}")
            }
        }
    }
}


@Preview(device = Devices.PIXEL_2_XL)
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