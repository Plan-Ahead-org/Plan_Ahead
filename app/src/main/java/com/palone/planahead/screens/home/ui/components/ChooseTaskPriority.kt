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
        Text(text = "Priority: " + sliderPosition.value.toTaskPriority().name) //TODO take this to string res
        Slider(
            steps = 3,
            value = sliderPosition.value,
            onValueChange = { sliderPosition.value = it },
            valueRange = 0.0f..50.0f,
            onValueChangeFinished = {
                onValueChange(sliderPosition.value.toTaskPriority())
            }
        )
    }

}

private fun Float.toTaskPriority(): TaskPriority {
    return when (this) {
        in 10.1f..20f -> TaskPriority.LOW
        in 20.1f..30f -> TaskPriority.MEDIUM
        in 30.1f..40f -> TaskPriority.HIGH
        in 40.1f..50f -> TaskPriority.CRITICAL
        else -> TaskPriority.INFORMATIONAL
    }
}
