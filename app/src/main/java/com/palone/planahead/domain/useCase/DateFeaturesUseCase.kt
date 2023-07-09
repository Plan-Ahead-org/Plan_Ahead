package com.palone.planahead.domain.useCase

import java.time.DayOfWeek
import java.time.LocalDateTime

interface DateFeaturesUseCase {
    fun getDaysToNextDayOfWeek(day: DayOfWeek): Int
    fun getEpochMillisFromLocalDate(dateAndTime: LocalDateTime): Long
}