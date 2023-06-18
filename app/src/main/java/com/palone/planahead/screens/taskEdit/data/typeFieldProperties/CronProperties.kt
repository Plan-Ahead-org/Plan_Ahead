package com.palone.planahead.screens.taskEdit.data.typeFieldProperties

import com.palone.planahead.screens.taskEdit.data.TaskRepeatPeriod
import java.time.DayOfWeek
import java.time.LocalTime

data class CronProperties(
    val repeatMode: TaskRepeatPeriod = TaskRepeatPeriod.WEEKLY,
    val dayTime: LocalTime = LocalTime.now(),
    val daysOfWeek: List<DayOfWeek> = listOf()
)