package com.palone.planahead.screens.home.ui.components.segmentedButton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                label = label,
                isChecked = label == selectedField,
                roundedCornerEnd = if (index == fields.size - 1) 10.dp else 0.dp,
                roundedCornerStart = if (index == 0) 10.dp else 0.dp,
                onValueChange = onValueChange
            )
        }

    }
}