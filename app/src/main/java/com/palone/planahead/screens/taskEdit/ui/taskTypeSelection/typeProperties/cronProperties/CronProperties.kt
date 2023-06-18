package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.cronProperties

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.screens.taskEdit.data.TaskRepeatPeriod
import com.palone.planahead.screens.taskEdit.ui.segmentedButton.SegmentedRadioButton
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
fun CronProperties(
    modifier: Modifier = Modifier,
    selectedTaskRepeatPeriod: TaskRepeatPeriod,
    selectedDaysOfWeek: List<DayOfWeek>,
    selectedTime: LocalTime,
    onModeChange: (mode: TaskRepeatPeriod) -> Unit,
    onDaysOfWeekChange: (days: List<DayOfWeek>) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
) {
    Column(modifier = modifier) {
        Row {
            Text(text = "Repeat:")
            SegmentedRadioButton(
                fields = TaskRepeatPeriod.values(),
                selectedField = selectedTaskRepeatPeriod,
                onValueChange = { onModeChange(it) })
        }
        when (selectedTaskRepeatPeriod) {
            TaskRepeatPeriod.DAILY -> {}
            TaskRepeatPeriod.WEEKLY -> {
                WeekDaysList(selectedDays = selectedDaysOfWeek, onValueChange = onDaysOfWeekChange)
            }

            TaskRepeatPeriod.MONTHLY -> {} // TODO
        }
        RepeatTimeSelector(selectedTime = selectedTime, onTimeChange = onTimeChange)
    }
}

@Composable
fun DisplayClock(modifier: Modifier = Modifier, time: LocalTime, onClick: () -> Unit) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(3.dp)
            ) {
                Text(text = time.hour.toString())
                Text(text = ":")
                Text(text = time.minute.toString())
            }
        }
    }
}