package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.cronProperties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.palone.planahead.screens.taskEdit.data.TaskRepeatPeriod
import com.palone.planahead.screens.taskEdit.ui.expandableList.ExpandableList
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
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            Text(text = "Repeat:")
            ExpandableList(
                fields = TaskRepeatPeriod.values().toList(),
                selectedField = selectedTaskRepeatPeriod,
                onValueChange = onModeChange
            )
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

