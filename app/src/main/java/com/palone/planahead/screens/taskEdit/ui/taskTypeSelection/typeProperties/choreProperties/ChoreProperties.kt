package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.choreProperties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.screens.taskEdit.ui.expandableList.ExpandableList
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.DateField
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun ChoreProperties(
    modifier: Modifier = Modifier,
    dateAndTime: LocalDateTime,
    selectedIntervalValue: Int,
    selectedIntervalUnit: ChronoUnit,
    onNewDateTime: (dateTime: LocalDateTime) -> Unit,
    onIntervalPropertiesChange: (intervalValue: Int, intervalUnit: ChronoUnit) -> Unit
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        DateField(
            modifier.fillMaxWidth(),
            todayDateAndTime = dateAndTime,
            onNewValue = onNewDateTime
        )
    }
    Row(
        modifier = Modifier.padding(top = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Repeat: every")
        IntervalValueSetter(
            selectedIntervalValue,
            onIntervalValueChange = {
                onIntervalPropertiesChange(
                    it,
                    selectedIntervalUnit
                )
            }
        )
        Spacer(modifier = Modifier.width(5.dp))
        IntervalUnitSetter(
            selectedIntervalUnit,
            onIntervalUnitChange = {
                onIntervalPropertiesChange(
                    selectedIntervalValue,
                    it
                )
            }
        )
    }
}


@Composable
private fun IntervalValueSetter(
    selectedIntervalValue: Int,
    onIntervalValueChange: (intervalValue: Int) -> Unit
) {
    ExpandableList(
        fields = listOf(1, 2, 5, 10, 15),
        selectedField = selectedIntervalValue,
        onValueChange = { onIntervalValueChange(it) })
}

@Composable
private fun IntervalUnitSetter(
    selectedIntervalUnit: ChronoUnit,
    onIntervalUnitChange: (intervalUnit: ChronoUnit) -> Unit,
) {
    ExpandableList(
        fields = listOf(
            ChronoUnit.MINUTES,
            ChronoUnit.HOURS,
            ChronoUnit.DAYS,
            ChronoUnit.WEEKS
        ),
        selectedField = selectedIntervalUnit,
        onValueChange = {
            onIntervalUnitChange(
                it
            )
        }
    )
}