package com.palone.planahead.domain.useCase

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class DateFeaturesUseCase {
    fun getDaysToNextDayOfWeek(day: DayOfWeek): Int {
        var nearestDay = LocalDate.now()
        var count = 0
        while (nearestDay.dayOfWeek.value != day.value) {
            nearestDay = nearestDay.plusDays(1)
            count++
        }
        return count
    }

    fun getEpochMillisFromLocalDate(dateAndTime: LocalDateTime): Long {
        val instant = dateAndTime.toInstant(ZoneId.systemDefault().rules.getOffset(dateAndTime))
        return instant.toEpochMilli()
    }
}