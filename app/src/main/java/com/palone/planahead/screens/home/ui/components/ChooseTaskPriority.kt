package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.palone.planahead.data.database.task.properties.TaskPriority

@Composable
fun ChooseTaskPriority(
    modifier: Modifier = Modifier,
    selectedPriority: TaskPriority,
    onValueChange: (TaskPriority) -> Unit
) {
    val sliderPosition = remember { mutableStateOf(0.0f) }
    Column(
        modifier = modifier.fillMaxWidth(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Priority: " + mapRangeToPriority(sliderPosition.value).name) //TODO take this to string res
        Slider(
            steps = 1,
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
