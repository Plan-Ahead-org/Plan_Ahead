package com.palone.planahead.screens.taskEdit.ui.notificationSelection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.screens.taskEdit.ui.segmentedButton.SegmentedRadioButton
import com.palone.planahead.screens.taskEdit.ui.selectors.TimeSelector
import java.time.LocalTime

@Composable
fun EditableAlertItem(
    modifier: Modifier = Modifier,
    type: AlertType,
    offsetTime: LocalTime,
    onTypeChange: (type: AlertType) -> Unit,
    onIntervalPropertiesChange: (time: LocalTime) -> Unit,
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text(text = "remind before")
            TimeSelector(selectedTime = offsetTime, onNewValue = onIntervalPropertiesChange)
        }
        SegmentedRadioButton(
            fields = AlertType.values(),
            selectedField = type,
            onValueChange = { onTypeChange(it) })
    }
}
