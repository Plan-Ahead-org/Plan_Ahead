package com.palone.planahead.screens.taskEdit.data.typeFieldProperties

import com.palone.planahead.screens.taskEdit.data.TaskRepeatMode
import java.time.DayOfWeek
import java.time.LocalTime

data class CronProperties(
    val repeatMode: TaskRepeatMode = TaskRepeatMode.WEEKLY,
    val dayTime: LocalTime = LocalTime.now(),
    val daysOfWeek: List<DayOfWeek> = listOf()
)