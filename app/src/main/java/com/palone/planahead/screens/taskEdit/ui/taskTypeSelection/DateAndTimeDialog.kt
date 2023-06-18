package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTimeDialog(onFinish: (LocalDateTime) -> Unit) {
    val selectedDate = remember {
        mutableStateOf(LocalDate.now())
    }
    val selectedTime = remember { mutableStateOf(LocalTime.now()) }
    val clockDialogState = rememberUseCaseState(visible = false,
        onCloseRequest = {
            onFinish(LocalDateTime.of(selectedDate.value, selectedTime.value))
        }
    )
    val calendarDialogState = rememberUseCaseState(visible = true,
        onFinishedRequest = {
            clockDialogState.show()
        },
        onDismissRequest = {
            onFinish(LocalDateTime.of(selectedDate.value, selectedTime.value))
        }
    )

    CalendarDialog(
        state = calendarDialogState,
        selection = CalendarSelection.Date {
            calendarDialogState.finish()
            selectedDate.value = it
        }
    )
    ClockDialog(
        state = clockDialogState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            selectedTime.value = LocalTime.of(hours, minutes)
            clockDialogState.finish()
            onFinish(LocalDateTime.of(selectedDate.value, selectedTime.value))
        }
    )
}