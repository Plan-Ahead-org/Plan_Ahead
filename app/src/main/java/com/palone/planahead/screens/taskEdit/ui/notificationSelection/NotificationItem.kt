package com.palone.planahead.screens.taskEdit.ui.notificationSelection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.screens.taskEdit.ui.expandableList.ExpandableList
import com.palone.planahead.screens.taskEdit.ui.segmentedButton.SegmentedRadioButton
import java.time.temporal.ChronoUnit

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    type: AlertType,
    intervalValue: Int,
    intervalUnit: ChronoUnit,
    onTypeChange: (type: AlertType) -> Unit,
    onIntervalPropertiesChange: (value: Int, unit: ChronoUnit) -> Unit,
    deleteContent: @Composable() (RowScope.() -> Unit) = {}
) {
    Column(modifier) {
        Row {
            Text(text = "Type: ")
            SegmentedRadioButton(
                fields = AlertType.values().toList(),
                selectedField = type,
                onValueChange = { onTypeChange(it as AlertType) })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IntervalProperties(intervalValue, onIntervalPropertiesChange, intervalUnit)
            deleteContent()
        }
    }

}

@Composable
private fun IntervalProperties(
    intervalValue: Int,
    onIntervalPropertiesChange: (value: Int, unit: ChronoUnit) -> Unit,
    intervalUnit: ChronoUnit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = "When: ")
        IntervalValueSetter(
            intervalValue,
            onIntervalValueChange = { onIntervalPropertiesChange(it, intervalUnit) })
        Spacer(modifier = Modifier.width(5.dp))
        IntervalUnitSetter(
            intervalUnit,
            onUnitChange = { onIntervalPropertiesChange(intervalValue, it) })
        Text(text = " before")
    }

}

@Composable
private fun IntervalUnitSetter(
    intervalUnit: ChronoUnit,
    onUnitChange: (unit: ChronoUnit) -> Unit
) {
    ExpandableList(
        fields = listOf(
            ChronoUnit.MINUTES,
            ChronoUnit.HOURS,
            ChronoUnit.DAYS,
            ChronoUnit.WEEKS
        ),
        selectedField = intervalUnit,
        onValueChange = {
            onUnitChange(
                it as ChronoUnit
            )
        })
}

@Composable
private fun IntervalValueSetter(
    intervalValue: Int,
    onIntervalValueChange: (value: Int) -> Unit,
) {
    ExpandableList(
        fields = listOf(1, 2, 5, 10, 15),
        selectedField = intervalValue,
        onValueChange = { onIntervalValueChange(it as Int) },
        customEntryContent = { onValueChange, shouldExpand ->
            CustomIntervalItem(shouldExpand, onValueChange, intervalValue)
        })
}

@Composable
private fun CustomIntervalItem(
    shouldExpand: MutableState<Boolean>,
    onValueChange: (Any) -> Unit,
    intervalValue: Int
) {
    val shouldShowDialog = remember {
        mutableStateOf(false)
    }
    Button(onClick = { shouldShowDialog.value = true }) {
        Text(text = "Custom")
    }
    if (shouldShowDialog.value)
        SetInputDialog(onReturn = {
            shouldExpand.value = false
            onValueChange(it)
        }, intervalValue)
}

@Composable
private fun SetInputDialog(
    onReturn: (Int) -> Unit,
    intervalValue: Int
) {
    Dialog(onDismissRequest = {
        onReturn(intervalValue)
    }) {
        val value = remember {
            mutableStateOf(intervalValue.toString())
        }
        Column {
            TextField(
                value = value.value,
                onValueChange = { value.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(onClick = {
                onReturn(value.value.toInt())
            }) {
                Text(text = "All set :)")
            }
        }
    }
}