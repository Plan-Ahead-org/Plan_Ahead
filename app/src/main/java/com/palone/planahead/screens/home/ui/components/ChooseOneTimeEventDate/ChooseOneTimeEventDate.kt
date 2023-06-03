package com.palone.planahead.screens.home.ui.components.ChooseOneTimeEventDate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.palone.planahead.screens.home.ui.components.chooseChoreTimes.ChooseChoreInterval
import java.util.TimeZone
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseOneTimeEventDate(
    modifier: Modifier = Modifier,
    onValueChange: (selectedDateMillis: Long, deadline: Long?, timeBeforeDeadlineAlert: List<Long>?) -> Unit,
    showDeadlineOption: Boolean = true
) {
    // ----------------START DATE---------------------
    val timeToNotifyBeforeDeadline = remember { mutableStateListOf(0L) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    val shouldShowDatePicker = remember {
        mutableStateOf(false)
    }
    val shouldShowTimePicker = remember {
        mutableStateOf(false)
    }
    //-------------------DEADLINE---------------------
    val deadlineDatePickerState = rememberDatePickerState()
    val deadlineTimePickerState = rememberTimePickerState()

    val shouldShowDeadlineTimePicker = remember {
        mutableStateOf(false)
    }
    val shouldShowDeadlineDatePicker = remember {
        mutableStateOf(false)
    }
    val shouldShowTimeBeforeDeadlineDialog = remember {
        mutableStateOf(false)
    }
    //------------------------------------------------

    Column(modifier = modifier) {
        SelectorItem(
            onClick = { shouldShowDatePicker.value = true },
            text = "Selected date: ${datePickerState.toDefaultTimeZoneMillis()}"
        )
        SelectorItem(
            onClick = { shouldShowTimePicker.value = true },
            text = "Selected time: ${timePickerState.hour}:${timePickerState.minute}"
        )
        if (showDeadlineOption) {
            SelectorItem(
                onClick = { shouldShowDeadlineDatePicker.value = true },
                text = "Selected deadline date: ${deadlineDatePickerState.toDefaultTimeZoneMillis()}"
            )
            SelectorItem(
                onClick = { shouldShowDeadlineTimePicker.value = true },
                text = "Selected deadline time: ${deadlineTimePickerState.hour}:${deadlineTimePickerState.minute}"
            )
            Text(text = "time to notify before end")
            timeToNotifyBeforeDeadline.forEach {
                Text(text = millisToDate(it))
            }
            Text(
                text = "Add more triggers",
                modifier = Modifier.clickable { shouldShowTimeBeforeDeadlineDialog.value = true })
        }
    }

    if (shouldShowDatePicker.value)
        PickerDialog(onDismissRequest = { shouldShowDatePicker.value = false },
            onClickRequest = {
                onValueChange(
                    returnOnValueChange(datePickerState, timePickerState),
                    returnOnValueChange(deadlineDatePickerState, deadlineTimePickerState),
                    timeToNotifyBeforeDeadline
                )
                shouldShowDatePicker.value = false
            }) {
            DatePicker(state = datePickerState)
        }
    if (shouldShowDeadlineDatePicker.value)
        PickerDialog(onDismissRequest = { shouldShowDatePicker.value = false },
            onClickRequest = {
                onValueChange(
                    returnOnValueChange(datePickerState, timePickerState),
                    returnOnValueChange(deadlineDatePickerState, deadlineTimePickerState),
                    timeToNotifyBeforeDeadline
                )
                shouldShowDeadlineDatePicker.value = false
            }) {
            DatePicker(state = deadlineDatePickerState)
        }
    if (shouldShowTimePicker.value)
        PickerDialog(onDismissRequest = { shouldShowTimePicker.value = false },
            onClickRequest = {
                onValueChange(
                    returnOnValueChange(datePickerState, timePickerState),
                    returnOnValueChange(deadlineDatePickerState, deadlineTimePickerState),
                    timeToNotifyBeforeDeadline
                )
                shouldShowTimePicker.value = false
            }) {
            TimePicker(modifier = Modifier.fillMaxWidth(), state = timePickerState)
        }
    if (shouldShowDeadlineTimePicker.value)
        PickerDialog(
            onDismissRequest = { shouldShowDeadlineTimePicker.value = false },
            onClickRequest = {
                onValueChange(
                    returnOnValueChange(datePickerState, timePickerState),
                    returnOnValueChange(deadlineDatePickerState, deadlineTimePickerState),
                    timeToNotifyBeforeDeadline
                )
                shouldShowDeadlineTimePicker.value = false
            }) {
            TimePicker(modifier = Modifier.fillMaxWidth(), state = deadlineTimePickerState)
        }
    if (shouldShowTimeBeforeDeadlineDialog.value)
        TimeBeforeDeadlineDialog(onFinish = {
            shouldShowTimeBeforeDeadlineDialog.value = false
            if (it != 0L) timeToNotifyBeforeDeadline.add(it)
        })
}

//TODO make preview

@Composable
fun TimeBeforeDeadlineDialog(onFinish: (millis: Long) -> Unit) {
    val millis = remember { mutableStateOf(0L) }
    Dialog(onDismissRequest = { onFinish(millis.value) }) {
        ChooseChoreInterval(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { millis.value = it })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun returnOnValueChange(
    datePickerState: DatePickerState,
    timePickerState: TimePickerState,
): Long {
    val date = datePickerState.toDefaultTimeZoneMillis()
    val timeFromTimePicker =
        (timePickerState.hour * 3600000).plus(timePickerState.minute * 60000)
    return ((date ?: 0) + timeFromTimePicker)
}

@OptIn(ExperimentalMaterial3Api::class)
private fun DatePickerState.toDefaultTimeZoneMillis(): Long? {
    return selectedDateMillis?.minus(
        TimeZone.getDefault().getOffset(
            selectedDateMillis!!
        )
    )
}

fun millisToDate(millis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    return "$hours:$minutes"
}
