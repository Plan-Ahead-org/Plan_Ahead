package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.palone.planahead.data.database.task.properties.TaskPriority

@Composable
fun ChooseTaskPriority(
    modifier: Modifier = Modifier,
    selectedPriority: TaskPriority,
    onValueChange: (TaskPriority) -> Unit
) {
    val sliderPosition = remember { mutableStateOf(mapPriorityToRange(selectedPriority)) }
    Column(
        modifier = modifier.fillMaxWidth(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Priority: " + mapRangeToPriority(sliderPosition.value).name) //TODO take this to string res
        Slider(
            steps = TaskPriority.values().size - 2,
            value = sliderPosition.value,
            onValueChange = { sliderPosition.value = it },
            valueRange = 0.0f..30.0f,
            onValueChangeFinished = {
                onValueChange(mapRangeToPriority(sliderPosition.value))
            }
        )
    }
}

private fun mapRangeToPriority(value: Float): TaskPriority {
    return when (value) {
        0f -> TaskPriority.LOW
        15f -> TaskPriority.MEDIUM
        30f -> TaskPriority.HIGH
        else -> TaskPriority.LOW
    }
}

private fun mapPriorityToRange(priority: TaskPriority): Float {
    return when (priority) {
        TaskPriority.LOW -> 0f
        TaskPriority.MEDIUM -> 15f
        TaskPriority.HIGH -> 30f
    }
}

@Preview
@Composable
fun ChooseTaskPriorityPreview() {
    ChooseTaskPriority(selectedPriority = TaskPriority.LOW, onValueChange = {})
}


@Preview(name = "Medium priority")
@Composable
fun ChooseTaskPriorityMediumPreview() {
    ChooseTaskPriority(selectedPriority = TaskPriority.MEDIUM, onValueChange = {})
}

@Preview(name = "High priority")
@Composable
fun ChooseTaskPriorityHighPreview() {
    ChooseTaskPriority(selectedPriority = TaskPriority.HIGH, onValueChange = {})
}