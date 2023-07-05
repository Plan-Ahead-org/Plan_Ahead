package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.screens.taskEdit.ui.selectors.DateSelector
import com.palone.planahead.screens.taskEdit.ui.selectors.TimeSelector
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun DateField(
    modifier: Modifier = Modifier,
    todayDateAndTime: LocalDateTime,
    onNewValue: (dateTime: LocalDateTime) -> Unit
) {
    val selectedHour = remember {
        mutableStateOf(0)
    }
    val selectedMinute = remember {
        mutableStateOf(0)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        DateSelector(
            modifier = Modifier.width(180.dp),
            todayDateAndTime = todayDateAndTime,
            onNewValue = {
                onNewValue(
                    it.withHour(selectedHour.value)
                        .withMinute(selectedMinute.value)
                )
            })

        TimeSelector(
            modifier = Modifier.width(160.dp),
            selectedTime = todayDateAndTime.toLocalTime(),
            onNewValue = {
                selectedHour.value = it.hour
                selectedMinute.value = it.minute
                onNewValue(
                    todayDateAndTime.withHour(selectedHour.value)
                        .withMinute(selectedMinute.value)
                )
            },
        )
    }
}


fun getYearDays(year: Int): List<Long> {
    var localDateTime = LocalDateTime.of(year, 1, 1, 0, 0)
    val listOfDays = mutableListOf<Long>()
    while (localDateTime.year == year) {
        listOfDays.add(getEpochMillisFromLocalDateTime(localDateTime))
        localDateTime = localDateTime.plusDays(1)
    }
    Log.i("days:", listOfDays.toString())
    return listOfDays
}

fun getEpochMillisFromLocalDateTime(dateAndTime: LocalDateTime): Long {
    val instant = dateAndTime.toInstant(ZoneId.systemDefault().rules.getOffset(dateAndTime))
    return instant.toEpochMilli()
}

fun getLocalDateTimeFromEpochMillis(millis: Long): LocalDateTime {
    val instant = Instant.ofEpochMilli(millis)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

