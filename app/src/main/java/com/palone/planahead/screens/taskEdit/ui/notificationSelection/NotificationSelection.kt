package com.palone.planahead.screens.taskEdit.ui.notificationSelection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.screens.taskEdit.data.AlertProperty
import java.time.temporal.ChronoUnit

@Composable
fun NotificationSelection(
    modifier: Modifier = Modifier,
    editAlertProperty: (Int, AlertProperty) -> Unit = { _, _ -> },
    deleteAlertProperty: (Int) -> Unit = { },
    notifications: List<AlertProperty> = emptyList()
) {
    Card(
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.onSecondary),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.background(Color.Transparent)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Notifications (${notifications.size})")
            notifications.forEachIndexed { index, item ->
                NotificationItem(
                    type = item.type,
                    intervalValue = item.offsetValue,
                    intervalUnit = item.offsetUnit,
                    onTypeChange = { type ->
                        editAlertProperty(index, notifications[index].copy(type = type))

                    },
                    onIntervalPropertiesChange = { value, unit ->
                        editAlertProperty(
                            index,
                            notifications[index].copy(
                                offsetValue = value,
                                offsetUnit = unit
                            )
                        )
                    },
                    deleteContent = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Notification",
                            modifier = Modifier.clickable { deleteAlertProperty(index) }
                        )
                    }
                )
                if (index != notifications.size - 1)
                    Divider(
                        modifier = Modifier
                            .padding(top = 6.dp, bottom = 3.dp)
                            .fillMaxWidth()
                    )
            }
        }
    }
}

@Preview(name = "Empty")
@Composable
fun EmptyNotificationSelectionPreview() {
    NotificationSelection(
        notifications = emptyList()
    )
}

@Preview(name = "2 items")
@Composable
fun FullNotificationSelectionPreview() {
    NotificationSelection(
        notifications = listOf(
            AlertProperty(
                type = AlertType.ALARM,
                offsetValue = 1,
                offsetUnit = ChronoUnit.DAYS
            ),
            AlertProperty(
                type = AlertType.PERSISTENT_NOTIFICATION,
                offsetValue = 1,
                offsetUnit = ChronoUnit.HOURS
            )
        )
    )
}

