package com.palone.planahead.screens.taskEdit.ui.notificationSelection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.data.database.alert.properties.AlertType
import java.time.LocalTime

@Composable
fun AlertItem(
    modifier: Modifier = Modifier,
    type: AlertType,
    offsetTime: LocalTime,
    deleteContent: @Composable() (RowScope.() -> Unit) = {}
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = type.name.lowercase())
            if (offsetTime.hour > 1) {
                Text(text = "${offsetTime.hour} hours")
            }
            if (offsetTime.hour == 1) {
                Text(text = "${offsetTime.hour} hour")
            }
            if (offsetTime.minute > 0) {
                Text(text = "${offsetTime.minute} min")
            }
            Text(text = "before")
        }
        deleteContent()
    }
}