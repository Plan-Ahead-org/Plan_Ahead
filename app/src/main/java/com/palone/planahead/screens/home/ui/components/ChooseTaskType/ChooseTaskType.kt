package com.palone.planahead.screens.home.ui.components.ChooseTaskType

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.palone.planahead.data.database.task.properties.TaskType

@Composable
fun ChooseTaskType(
    modifier: Modifier = Modifier,
    selectedTaskType: TaskType,
    onValueChange: (TaskType) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        TaskTypeSegmentedButtonItem(
            taskType = TaskType.CRON,
            selectedTaskType = selectedTaskType,
            roundedCornerStart = 10.dp,
            roundedCornerEnd = 0.dp,
            onValueChange = onValueChange
        )
        TaskTypeSegmentedButtonItem(
            taskType = TaskType.CHORE,
            selectedTaskType = selectedTaskType,
            roundedCornerStart = 0.dp,
            roundedCornerEnd = 0.dp,
            onValueChange = onValueChange
        )
        TaskTypeSegmentedButtonItem(
            taskType = TaskType.ONE_TIME,
            selectedTaskType = selectedTaskType,
            roundedCornerStart = 0.dp,
            roundedCornerEnd = 10.dp,
            onValueChange = onValueChange
        )

    }
}

@Composable
@Preview
fun PreviewChooseAlertTrigger() {
    ChooseTaskType(selectedTaskType = TaskType.ONE_TIME) {}
}