package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.cronProperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.screens.taskEdit.ui.selectors.TimeSelector
import java.time.LocalTime

@Composable
fun RepeatTimeSelector(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime,
    onTimeChange: (LocalTime) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Choose time", modifier = Modifier)
        DisplayClock(time = selectedTime, onNewValue = onTimeChange, modifier = Modifier)
    }
}

@Composable
fun DisplayClock(
    modifier: Modifier = Modifier,
    time: LocalTime,
    onNewValue: (time: LocalTime) -> Unit
) {
    TimeSelector(modifier = modifier.width(150.dp), selectedTime = time, onNewValue = onNewValue)
}