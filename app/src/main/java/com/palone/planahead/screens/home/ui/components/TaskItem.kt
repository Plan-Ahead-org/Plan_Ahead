package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskType
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun TaskItem(
    task: Task,
    alerts: List<Alert>,
) {
    val alertTypes = remember { mutableStateListOf<AlertType>() }
    val alertTriggers = remember { mutableStateListOf<AlertTrigger>() }

    SideEffect {
        alerts.forEach { alert ->
            if (!alertTriggers.contains(alert.alertTriggerName)) alertTriggers.add(alert.alertTriggerName)
            if (!alertTypes.contains(alert.alertTypeName)) alertTypes.add(alert.alertTypeName)
        }
    }
    ItemContent(task, alerts)
}

@Composable
private fun ItemContent(
    task: Task,
    alerts: List<Alert>
) {
    val taskDateTime by remember {
        mutableStateOf(task.eventMillisInEpoch?.let {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
        })
    }
    val todayDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    val isToday by remember {
        mutableStateOf(taskDateTime?.toLocalDate() == todayDateTime?.toLocalDate())
    }
//    val hasPassedToday = ((taskDateTime?.toLocalTime()?.compareTo(todayDateTime.toLocalTime())
//        ?: 0) < 0) && isToday // Only valid if today
    val hasPassed by remember {
        mutableStateOf(
            getEpochMillisFromLocalDate(
                taskDateTime ?: todayDateTime
            ) < getEpochMillisFromLocalDate(
                todayDateTime
            )
        )
    }

    val isTomorrow by remember {
        mutableStateOf(taskDateTime?.toLocalDate()?.minusDays(1) == todayDateTime?.toLocalDate())
    }
    Card(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp / 10)) {
        Row(
            modifier = Modifier
//                .clickable { shouldExpand.value = !shouldExpand.value }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TaskTriggerPillar(hasPassed)
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = task.description,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                EventDate(
                    task,
                    isToday,
                    taskDateTime,
                    hasPassed,
                    isTomorrow
                )
                Row {
                    alerts.forEach {
                        AlertItem(alert = it, task = task)
                    }
                    Text(text = " | Priority:  ${task.priority}")
                }
            }
        }
    }
}

@Composable
private fun TaskTriggerPillar(hasPassed: Boolean) {
    Card(
        modifier = Modifier
            .width(25.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = if (hasPassed) Color.Red else Color.Cyan)
    ) {
        Icon(Icons.Default.Timer, null)
    }
}

@Composable
private fun EventDate(
    task: Task,
    isToday: Boolean,
    taskDateTime: LocalDateTime?,
    hasPassed: Boolean,
    isTomorrow: Boolean,
) {
    val taskDate by remember {
        mutableStateOf(task.eventMillisInEpoch)
    }
    val pattern by remember {
        mutableStateOf("yyyy-MM-dd HH:mm")
    }
    val simpleDateFormat by remember {
        mutableStateOf(SimpleDateFormat(pattern, Locale.getDefault()))
    }
    val formattedTaskDate by remember {
        mutableStateOf(
            taskDate?.let { it1 -> simpleDateFormat.format(it1) } // Is it ok doing it here?
        )
    }
    if (task.taskType == TaskType.ONE_TIME) {
        if (isToday) {
            Text(
                text = "Today at ${taskDateTime?.toLocalTime()}",
                color = if (hasPassed) Color.Red else Color.Black
            )
        } else if (isTomorrow) {
            Text(text = "Tomorrow at ${taskDateTime?.toLocalTime()}")
        } else {
            Text(text = "at $formattedTaskDate", color = if (hasPassed) Color.Red else Color.Black)
        }
    }
}

@Composable
fun AlertItem(
    alert: Alert,
    task: Task,
) {
    Spacer(modifier = Modifier.width(5.dp))
    when (alert.alertTypeName) {
        AlertType.ALARM -> {
            Icon(Icons.Default.Alarm, null)
        }

        AlertType.NOTIFICATION -> {
            Icon(Icons.Default.Notifications, null)
        }

        AlertType.PERSISTENT_NOTIFICATION -> {
            Icon(
                Icons.Default.NotificationImportant,
                null
            )
        }
    }
    Spacer(modifier = Modifier.width(5.dp))
    if (task.eventMillisInEpoch != null && alert.eventMillisInEpoch != null) {
        Text(
            text = Duration.of(
                alert.toTaskOffset,
                ChronoUnit.MILLIS
            ).toMinutes().toString() + " min,"
        )
    }
}

fun getEpochMillisFromLocalDate(dateAndTime: LocalDateTime): Long {
    val instant = dateAndTime.toInstant(ZoneId.systemDefault().rules.getOffset(dateAndTime))
    return instant.toEpochMilli()
}
