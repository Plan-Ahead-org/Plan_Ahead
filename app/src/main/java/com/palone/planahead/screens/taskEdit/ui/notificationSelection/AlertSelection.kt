package com.palone.planahead.screens.taskEdit.ui.notificationSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.palone.planahead.screens.taskEdit.TaskEditViewModel

@Composable
fun AlertSelection(modifier: Modifier = Modifier, viewModel: TaskEditViewModel) {
    val notifications = viewModel.alertProperties.collectAsState().value
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.background(Color.Transparent)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp), horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Reminders (${notifications.size})")
            notifications.forEachIndexed { index, item ->
                AlertItem(
                    modifier = Modifier.fillMaxWidth(),
                    type = item.type,
                    deleteContent = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete Notification",
                            modifier = Modifier.clickable { viewModel.deleteAlertProperty(index) }
                        )
                    },
                    offsetTime = item.offsetTime
                )
            }
        }
    }
}