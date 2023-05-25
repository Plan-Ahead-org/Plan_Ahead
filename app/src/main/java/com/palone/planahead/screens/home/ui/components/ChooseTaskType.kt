package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.palone.planahead.data.database.alert.properties.TaskType

@Composable
fun ChooseTaskType(
    modifier: Modifier = Modifier,
    selectedTaskType: TaskType,
    onValueChange: (TaskType) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {

        val uncheckedButtonDefaultColor =
            ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        val checkedButtonDefaultColor = ButtonDefaults.buttonColors()

        Button(//Not yet implemented in MaterialDesign 3
            colors = if (selectedTaskType == TaskType.CHORE) checkedButtonDefaultColor else uncheckedButtonDefaultColor,
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 0.dp,
                bottomStart = 10.dp,
                bottomEnd = 0.dp
            ), onClick = { onValueChange(TaskType.CHORE) }) {
            if (selectedTaskType == TaskType.CHORE)
                Icon(imageVector = Icons.Default.Done, contentDescription = "checked")
            Text(text = TaskType.CHORE.name)
        }
        Button(//Not yet implemented in MaterialDesign 3
            colors = if (selectedTaskType == TaskType.CRON) checkedButtonDefaultColor else uncheckedButtonDefaultColor,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ), onClick = { onValueChange(TaskType.CRON) }) {
            if (selectedTaskType == TaskType.CRON)
                Icon(imageVector = Icons.Default.Done, contentDescription = "checked")
            Text(text = TaskType.CRON.name)
        }
        Button(//Not yet implemented in MaterialDesign 3
            colors = if (selectedTaskType == TaskType.ONE_TIME) checkedButtonDefaultColor else uncheckedButtonDefaultColor,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 10.dp,
                bottomStart = 0.dp,
                bottomEnd = 10.dp
            ), onClick = { onValueChange(TaskType.ONE_TIME) }) {
            if (selectedTaskType == TaskType.ONE_TIME)
                Icon(imageVector = Icons.Default.Done, contentDescription = "checked")
            Text(text = TaskType.ONE_TIME.name)
        }

    }
}

@Composable
@Preview
fun PreviewChooseAlertTrigger() {
    ChooseTaskType(selectedTaskType = TaskType.ONE_TIME) {}
}