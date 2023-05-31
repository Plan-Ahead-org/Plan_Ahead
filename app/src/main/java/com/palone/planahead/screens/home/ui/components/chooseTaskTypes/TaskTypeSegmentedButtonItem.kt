package com.palone.planahead.screens.home.ui.components.chooseTaskTypes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.palone.planahead.data.database.task.properties.TaskType

@Composable
fun TaskTypeSegmentedButtonItem(
    modifier: Modifier = Modifier,
    taskType: TaskType,
    selectedTaskType: TaskType,
    roundedCornerEnd: Dp,
    roundedCornerStart: Dp,
    onValueChange: (TaskType) -> Unit
) {
    val uncheckedButtonDefaultColor =
        ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    val checkedButtonDefaultColor = ButtonDefaults.buttonColors()
    Button(//Not yet implemented in MaterialDesign 3
        modifier = modifier,
        colors = if (selectedTaskType == taskType) checkedButtonDefaultColor else uncheckedButtonDefaultColor,
        shape = RoundedCornerShape(
            topStart = roundedCornerStart,
            topEnd = roundedCornerEnd,
            bottomStart = roundedCornerStart,
            bottomEnd = roundedCornerEnd
        ), onClick = { onValueChange(taskType) }) {
        if (selectedTaskType == taskType)
            Icon(imageVector = Icons.Default.Done, contentDescription = "checked")
        Text(text = taskType.name)
    }
}