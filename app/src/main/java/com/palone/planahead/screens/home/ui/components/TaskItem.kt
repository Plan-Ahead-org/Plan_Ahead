package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material.icons.outlined.Timer
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskPriority
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.ui.theme.PlanAheadColors
import com.palone.planahead.ui.theme.PlanAheadTheme
import com.palone.planahead.ui.theme.ThemedPreview
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

    val hasPassed by remember {
        mutableStateOf(
            getEpochMillisFromLocalDate(
                taskDateTime ?: todayDateTime
            ) > getEpochMillisFromLocalDate(
                todayDateTime
            )
        )
    }

    Card(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp / 10)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val taskTextColor =
                if (hasPassed) PlanAheadColors.colors.taskAfterDeadline else PlanAheadColors.colors.taskBeforeDeadline
            TaskTriggerPillar(hasPassed)
            Column(modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp)) {
                Text(
                    color = taskTextColor,
                    text = task.description,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                taskDateTime?.let { EventDate(task, it, hasPassed) }
                Row(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {

                    alerts.forEach {
                        AlertItem(alert = it, task = task)
                    }
                    Icon(
                        Icons.Outlined.PriorityHigh,
                        null,
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .offset(0.dp, 1.dp)
                    )
                    Text(text = "${task.priority}")
                }
            }
        }
    }
}

@Composable
private fun TaskTriggerPillar(hasPassed: Boolean) {
    val containerColor =
        if (hasPassed) PlanAheadColors.colors.taskAfterDeadlineContainer else PlanAheadColors.colors.taskBeforeDeadlineContainer
    Card(
        modifier = Modifier
            .width(35.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Icon(
            Icons.Outlined.Timer, null, modifier = Modifier
                .padding(0.dp, 7.dp)
                .fillMaxWidth()
        )
    }
}


fun getDateTimeDescription(dateTime: LocalDateTime): String {
    val todayDateTime = LocalDateTime.now()
    val isTomorrow = dateTime.toLocalDate()?.minusDays(1) == todayDateTime.toLocalDate()
    val isToday = dateTime.toLocalDate() == todayDateTime?.toLocalDate()
    val hourString = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
    val dateString = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("y-MM-dd"))

    if (isTomorrow) {
        return "Tomorrow at $hourString"
    }
    if (isToday) {
        return "Today at $hourString"
    }
    return "$dateString at $hourString"
}

@Composable
private fun EventDate(
    task: Task,
    taskDateTime: LocalDateTime,
    hasPassed: Boolean
) {
    val taskTextColor =
        if (hasPassed) PlanAheadColors.colors.taskAfterDeadline else PlanAheadColors.colors.taskBeforeDeadline
    val taskDateDescription by remember {
        mutableStateOf({
            getDateTimeDescription(taskDateTime)
        })
    }

    if (task.taskType == TaskType.ONE_TIME) {
        Text(
            text = taskDateDescription(),
            color = taskTextColor
        )
    }
}

@Composable
fun AlertItem(
    alert: Alert,
    task: Task,
) {
    val icon = when (alert.alertTypeName) {
        AlertType.ALARM -> Icons.Default.Alarm
        AlertType.NOTIFICATION -> Icons.Default.Notifications
        AlertType.PERSISTENT_NOTIFICATION -> Icons.Default.NotificationImportant
    }
    Icon(icon, null, modifier = Modifier.offset(0.dp, 1.dp))
    Spacer(modifier = Modifier.width(3.dp))
    if (task.eventMillisInEpoch != null && alert.eventMillisInEpoch != null) {
        Text(
            text = Duration.of(
                alert.toTaskOffset,
                ChronoUnit.MILLIS
            ).toMinutes().toString() + " min"
        )
    }
    Spacer(modifier = Modifier.width(10.dp))
}

fun getEpochMillisFromLocalDate(dateAndTime: LocalDateTime): Long {
    val instant = dateAndTime.toInstant(ZoneId.systemDefault().rules.getOffset(dateAndTime))
    return instant.toEpochMilli()
}

@ThemedPreview(name = "Task item - before deadline")
@Composable
fun TaskItemBeforeDeadlinePreview() {
    PlanAheadTheme {
        TaskItem(
            task = Task(
                description = "Test",
                taskType = TaskType.ONE_TIME,
                eventMillisInEpoch = LocalDateTime.now().plusMinutes(20).toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                priority = TaskPriority.MEDIUM,
                addedDate = "",
                isCompleted = false
            ),
            alerts = listOf(
                Alert(
                    alertTypeName = AlertType.ALARM,
                    alertTriggerName = AlertTrigger.TIME,
                    eventMillisInEpoch = LocalDateTime.now()
                        .toInstant(ZoneOffset.UTC)
                        .toEpochMilli(),
                    toTaskOffset = 500000
                )
            )
        )
    }
}

@ThemedPreview(name = "Task item - after deadline")
@Composable
fun TaskItemAfterDeadlinePreview() {
    PlanAheadTheme {
        TaskItem(
            task = Task(
                description = "Test",
                taskType = TaskType.ONE_TIME,
                eventMillisInEpoch = LocalDateTime.now().minusDays(3)
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                priority = TaskPriority.MEDIUM,
                addedDate = "",
                isCompleted = false
            ),
            alerts = listOf(
                Alert(
                    alertTypeName = AlertType.ALARM,
                    alertTriggerName = AlertTrigger.TIME,
                    eventMillisInEpoch = Instant.now().toEpochMilli() + 100000,
                    toTaskOffset = 500000
                )
            )
        )
    }
}

@Preview(name = "Task item - tomorrow")
@Composable
fun TaskItemTomorrowPreview() {
    PlanAheadTheme {
        TaskItem(
            task = Task(
                description = "Test",
                taskType = TaskType.ONE_TIME,
                eventMillisInEpoch = LocalDateTime.now().plusDays(1)
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                priority = TaskPriority.MEDIUM,
                addedDate = "",
                isCompleted = false
            ),
            alerts = listOf(
                Alert(
                    alertTypeName = AlertType.ALARM,
                    alertTriggerName = AlertTrigger.TIME,
                    eventMillisInEpoch = Instant.now().toEpochMilli() + 100000,
                    toTaskOffset = 500000
                )
            )
        )
    }
}


@Preview(name = "Task item - far ahead")
@Composable
fun TaskItemDatePreview() {
    PlanAheadTheme {
        TaskItem(
            task = Task(
                description = "Test",
                taskType = TaskType.ONE_TIME,
                eventMillisInEpoch = LocalDateTime.now().plusDays(5)
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                priority = TaskPriority.MEDIUM,
                addedDate = "",
                isCompleted = false
            ),
            alerts = listOf(
                Alert(
                    alertTypeName = AlertType.ALARM,
                    alertTriggerName = AlertTrigger.TIME,
                    eventMillisInEpoch = Instant.now().toEpochMilli() + 100000,
                    toTaskOffset = 500000
                ),

                Alert(
                    alertTypeName = AlertType.NOTIFICATION,
                    alertTriggerName = AlertTrigger.TIME,
                    eventMillisInEpoch = Instant.now().toEpochMilli() + 100000,
                    toTaskOffset = 500000
                ),

                Alert(
                    alertTypeName = AlertType.PERSISTENT_NOTIFICATION,
                    alertTriggerName = AlertTrigger.TIME,
                    eventMillisInEpoch = Instant.now().toEpochMilli() + 100000,
                    toTaskOffset = 500000
                )
            )
        )
    }
}