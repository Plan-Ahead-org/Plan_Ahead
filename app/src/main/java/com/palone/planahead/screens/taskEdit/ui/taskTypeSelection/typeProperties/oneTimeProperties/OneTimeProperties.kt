package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.oneTimeProperties

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.DateField
import java.time.LocalDateTime

@Composable
fun OneTimeProperties(
    modifier: Modifier = Modifier,
    dateAndTime: LocalDateTime,
    onNewDateTime: (dateTime: LocalDateTime) -> Unit
) {
    DateField(
        modifier = modifier.fillMaxWidth(),
        todayDateAndTime = dateAndTime,
        onNewValue = onNewDateTime
    )
}