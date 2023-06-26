package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.screens.taskEdit.TaskEditViewModel
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.DateAndTimeDialog
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.choreProperties.ChoreProperties
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.cronProperties.CronProperties
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.oneTimeProperties.OneTimeProperties
import java.time.temporal.ChronoUnit

@Composable
fun TypeProperties(modifier: Modifier = Modifier, viewModel: TaskEditViewModel) {

    val oneTimeProperties = viewModel.oneTimeProperties.collectAsState().value
    val choreProperties = viewModel.choreProperties.collectAsState().value
    val cronProperties = viewModel.cronProperties.collectAsState().value

    val shouldShowDateAndTimeDialog = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        when (viewModel.selectedTaskType.collectAsState().value) {
            TaskType.ONE_TIME -> OneTimeProperties(
                dateAndTime = oneTimeProperties.date,
                onChooseDateAndTimeEvent = { shouldShowDateAndTimeDialog.value = true }
            ) // TODO

            TaskType.CHORE ->
                ChoreProperties(
                    dateAndTime = choreProperties.date,
                    selectedIntervalValue = choreProperties.intervalValue,
                    selectedIntervalUnit = choreProperties.intervalUnit,
                    onChooseDateAndTimeEvent = { shouldShowDateAndTimeDialog.value = true }, // TODO
                    onIntervalPropertiesChange = { newIntervalValue: Int, newIntervalUnit: ChronoUnit ->
                        viewModel.updateIntervalValue(newIntervalValue)
                        viewModel.updateIntervalUnit(newIntervalUnit)
                    }
                )

            TaskType.CRON -> {
                CronProperties(
                    selectedTaskRepeatPeriod = cronProperties.repeatPeriod,
                    onModeChange = { newMode -> viewModel.updateTaskRepeatMode(newMode) },
                    selectedDaysOfWeek = cronProperties.daysOfWeek,
                    onDaysOfWeekChange = { newDays ->
                        viewModel.updateDaysOfWeek(newDays)
                    },
                    selectedTime = cronProperties.dayTime,
                    onTimeChange = { newTime -> viewModel.updateRepeatTime(newTime) }
                )
            }

        }
    }
    if (shouldShowDateAndTimeDialog.value)
        DateAndTimeDialog(onFinish = {
            viewModel.updateDateAndTime(it)
            shouldShowDateAndTimeDialog.value = false
        })

}