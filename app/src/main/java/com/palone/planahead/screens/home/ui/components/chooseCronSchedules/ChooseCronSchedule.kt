package com.palone.planahead.screens.home.ui.components.chooseCronSchedules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.palone.planahead.data.CronScheduleDateTime
import com.palone.planahead.screens.home.ui.components.chooseChoreTimes.ChooseChoreInterval
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCronSchedule(onValueChange: (selectedTimes: List<LocalDateTime>, interval: Long) -> Unit) {
    val interval = remember { mutableStateOf(0L) }
    val selectedDates = remember { mutableStateListOf<CronScheduleDateTime>() }
    val editedDate = remember { mutableStateOf(LocalDate.now()) }

    val calendarState = rememberUseCaseState(
        visible = false,
        onFinishedRequest = {
            onValueChange(
                castToLocalDateTime(selectedDates),
                interval.value
            )
        }
    )
    val clockState = rememberUseCaseState(
        visible = false,
        onFinishedRequest = {
            onValueChange(
                castToLocalDateTime(selectedDates),
                interval.value
            )
        })

    Button(onClick = { calendarState.show() }) {
        Text(text = "Set days") // TODO add this to string res
    }
    ChooseChoreInterval(onValueChange = { interval.value = it })
    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
        selectedDates.forEach { schedule ->
            CronScheduleItem(schedule = schedule, onClick = {
                editedDate.value = schedule.date
                clockState.show()
            })
            Divider()
        }
    }
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Dates(selectedDates = selectedDates.map { schedule -> schedule.date }) { newDates ->
            selectedDates.clear()
            newDates.forEach { selectedDates += listOf(CronScheduleDateTime(it, listOf())) }
        },
        config = CalendarConfig(style = CalendarStyle.MONTH),
    )
    ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes { hours, minutes ->
        val index = selectedDates.indexOf(selectedDates.find { it.date == editedDate.value })
        val newListOfTimes = selectedDates[index].time + listOf(LocalTime.of(hours, minutes))
        selectedDates[index] = selectedDates[index].copy(time = newListOfTimes)
    })
}

fun castToLocalDateTime(schedule: List<CronScheduleDateTime>): List<LocalDateTime> {
    val list = mutableListOf<LocalDateTime>()
    schedule.forEach { item ->
        item.time.forEach { time ->
            list.add(
                LocalDateTime.of(
                    item.date, time
                )
            )
        }
    }
    return list
}


