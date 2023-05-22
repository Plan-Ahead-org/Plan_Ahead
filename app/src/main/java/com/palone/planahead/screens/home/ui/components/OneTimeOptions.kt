package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneTimeOptions(onSelected: (String) -> Unit) {
    //Kalendarz
    //TimePicker
    val shouldShowDatePicker = remember { mutableStateOf(true) }
    val shouldShowTimePicker = remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    val lastKnownTime = remember { mutableStateOf(0) }
    if (shouldShowDatePicker.value)
        DatePicker(state = datePickerState)
    if (shouldShowTimePicker.value)
        TimePicker(state = timePickerState)
    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (datePickerState.selectedDateMillis != null) {
            shouldShowDatePicker.value = false
            shouldShowTimePicker.value = true
        }
    }
    LaunchedEffect(timePickerState.minute) {
        if (timePickerState.minute != lastKnownTime.value) {
            shouldShowDatePicker.value = false
            shouldShowTimePicker.value = false
        }
        onSelected( // TODO refactor
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(
                    datePickerState.selectedDateMillis ?: 0
                ), ZoneId.systemDefault()
            ).format(
                DateTimeFormatter.ISO_LOCAL_DATE
            ) + " ${timePickerState.hour}:${timePickerState.minute}"
        )
    }
    if (!(shouldShowDatePicker.value && shouldShowTimePicker.value))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(
                            datePickerState.selectedDateMillis ?: 0
                        ), ZoneId.systemDefault()
                    ).format(
                        DateTimeFormatter.ISO_LOCAL_DATE
                    )
                )
                Button(onClick = { shouldShowDatePicker.value = true }) {
                    Text(text = "Change date")
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${timePickerState.hour}:${timePickerState.minute}")
                Button(onClick = {
                    lastKnownTime.value = timePickerState.minute
                    shouldShowTimePicker.value = true
                }) {
                    Text(text = "Change time")
                }
            }

        }


}