package com.palone.planahead.screens.taskEdit.ui.segmentedButton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.palone.planahead.data.database.task.properties.TaskType

@Composable
fun SegmentedRadioButton(
    modifier: Modifier = Modifier,
    fields: List<Enum<*>>,
    selectedField: Enum<*>,
    onValueChange: (Enum<*>) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        fields.forEachIndexed { index, label ->
            SegmentedRadioButtonItem(
                modifier = Modifier.weight(1f),
                label = label,
                isChecked = label == selectedField,
                roundedCornerEnd = if (index == fields.size - 1) 20.dp else 0.dp,
                roundedCornerStart = if (index == 0) 20.dp else 0.dp,
                onValueChange = onValueChange
            )
        }

    }
}

@Preview
@Composable
fun Preview() {
    SegmentedRadioButton(
        fields = TaskType.values().toList(),
        selectedField = TaskType.CRON,
        onValueChange = {})
}