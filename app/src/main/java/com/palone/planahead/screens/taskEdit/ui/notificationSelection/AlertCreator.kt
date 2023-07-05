package com.palone.planahead.screens.taskEdit.ui.notificationSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.screens.taskEdit.data.AlertProperty

@Composable
fun AlertCreator(
    alert: AlertProperty,
    modifier: Modifier = Modifier,
    onValueChange: (AlertProperty) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary)
        ) {
            EditableAlertItem(
                modifier = Modifier.padding(10.dp),
                type = alert.type,
                onTypeChange = { onValueChange(alert.copy(type = it)) },
                onIntervalPropertiesChange = { time ->
                    onValueChange(
                        alert.copy(
                            offsetTime = time
                        )
                    )
                },
                offsetTime = alert.offsetTime
            )
        }
    }

}