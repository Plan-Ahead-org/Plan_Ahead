package com.palone.planahead.screens.taskEdit.ui.notificationSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.screens.taskEdit.data.AlertProperty

@Composable
fun NotificationCreator(
    modifier: Modifier = Modifier,
    onNotificationCreation: (AlertProperty) -> Unit
) {
    val notification = remember {
        mutableStateOf(AlertProperty())
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = contentColorFor(
                backgroundColor = MaterialTheme.colorScheme.outline
            )
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.background(Color.Transparent)
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
                NotificationItem(modifier = Modifier.padding(10.dp),
                    type = notification.value.type,
                    intervalValue = notification.value.offsetValue,
                    intervalUnit = notification.value.offsetUnit,
                    onTypeChange = { notification.value = notification.value.copy(type = it) },
                    onIntervalPropertiesChange = { value, unit ->
                        notification.value =
                            notification.value.copy(
                                offsetUnit = unit,
                                offsetValue = value
                            )
                    }
                )
            }
            Text(
                text = "+ Add this notification", fontSize = 20.sp, modifier = Modifier
                    .clickable {
                        onNotificationCreation(notification.value)
                        notification.value = AlertProperty()
                    }
                    .padding(20.dp)
            )
        }
    }

}

@Preview
@Composable
fun NotificationCreatorPreview() {
    NotificationCreator(
        onNotificationCreation = {}
    )
}