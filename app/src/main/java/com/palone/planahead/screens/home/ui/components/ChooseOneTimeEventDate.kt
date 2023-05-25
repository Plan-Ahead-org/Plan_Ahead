package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseOneTimeEventDate(modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    val shouldShowDatePicker = remember {
        mutableStateOf(false)
    }
    val shouldShowTimePicker = remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {
        Card(modifier = Modifier
            .clickable { shouldShowDatePicker.value = true }
            .padding(5.dp)) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Selected date: ${datePickerState.selectedDateMillis}" //TODO add this to string res
            )
        }
        Card(modifier = Modifier
            .clickable { shouldShowTimePicker.value = true }
            .padding(5.dp)) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Selected time: ${timePickerState.hour}:${timePickerState.minute}" //TODO add this to string res
            )
        }
    }


    if (shouldShowDatePicker.value)
        DatePickerDialog(
            onDismissRequest = { shouldShowDatePicker.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        onValueChange(returnOnValueChange(datePickerState, timePickerState))
                        shouldShowDatePicker.value = false
                    }) {
                    Text(text = "Ok")
                }
            }) {
            DatePicker(state = datePickerState)

        }
    if (shouldShowTimePicker.value)
        DatePickerDialog(
            onDismissRequest = { shouldShowTimePicker.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        onValueChange(returnOnValueChange(datePickerState, timePickerState))
                        shouldShowTimePicker.value = false
                    }) {
                    Text(text = "Ok")
                }
            }) {
            TimePicker(modifier = Modifier.fillMaxWidth(), state = timePickerState)
        }
}

//TODO make preview
@OptIn(ExperimentalMaterial3Api::class)
fun returnOnValueChange(
    datePickerState: DatePickerState,
    timePickerState: TimePickerState
): String {
    val date = datePickerState.selectedDateMillis?.minus(
        TimeZone.getDefault().getOffset(
            datePickerState.selectedDateMillis!!
        )
    )
    val timeFromTimePicker =
        (timePickerState.hour * 3600000).plus(timePickerState.minute * 60000)
    return ((date ?: 0) + timeFromTimePicker).toString()
}