package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.cronProperties

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RepeatTimeSelector(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime,
    onTimeChange: (LocalTime) -> Unit
) {
    val clockState = rememberUseCaseState(visible = false, onCloseRequest = {})
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "At: ")
        DisplayClock(time = selectedTime, onClick = { clockState.show() })
        ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes { hours, minutes ->
            onTimeChange(
                LocalTime.of(hours, minutes)
            )
        }
        )
    }

}