package com.palone.planahead.data

import java.time.LocalDate
import java.time.LocalTime

data class CronScheduleDateTime(val date: LocalDate, var time: List<LocalTime>)
