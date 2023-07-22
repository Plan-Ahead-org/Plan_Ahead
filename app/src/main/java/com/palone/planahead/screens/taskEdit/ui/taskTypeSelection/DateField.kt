package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateField(
    modifier: Modifier = Modifier,
    dateAndTime: LocalDateTime,
    onChooseDateAndTimeEvent: () -> Unit
) {
    val date = dateAndTime.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
    val time = dateAndTime.toLocalTime().format(
        DateTimeFormatter.ISO_LOCAL_TIME
    )
    OutlinedTextField(modifier = modifier,
        label = { Text(text = "Date") },
        value = "$date $time",
        onValueChange = {},
        readOnly = true,
        suffix = {
            Icon(
                Icons.Default.CalendarMonth,
                "Choose date and time",
                modifier = Modifier.clickable { onChooseDateAndTimeEvent() })
        }
    )
}

@Preview
@Composable
fun DateFieldPreview() {
    DateField(dateAndTime = LocalDateTime.now(), onChooseDateAndTimeEvent = {})
}