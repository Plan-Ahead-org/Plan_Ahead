package com.palone.planahead.screens.taskEdit.data.typeFieldProperties

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class ChoreProperties(
    val date: LocalDateTime = LocalDateTime.now(),
    val intervalValue: Int = 10,
    val intervalUnit: ChronoUnit = ChronoUnit.DAYS,
)
