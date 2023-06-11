package com.palone.planahead.screens.home.ui.components.chooseChoreTimes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.palone.planahead.screens.home.ui.components.ChooseOneTimeEventDate

@Composable
fun ChooseChoreTime(onValueChange: (Long, Long) -> Unit) {
    val eventField = remember { mutableStateOf(0L) }
    val intervalField = remember { mutableStateOf(0L) }

    ChooseOneTimeEventDate(onValueChange = {
        eventField.value = it
        onValueChange(eventField.value, intervalField.value)
    })
    ChooseChoreInterval(onValueChange = {
        intervalField.value = it
        onValueChange(eventField.value, intervalField.value)
    })
}