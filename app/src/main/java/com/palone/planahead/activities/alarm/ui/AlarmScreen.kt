package com.palone.planahead.activities.alarm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.palone.planahead.activities.alarm.AlarmViewModel
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import java.time.Duration
import java.time.temporal.ChronoUnit

@Composable
fun AlarmScreen(alert: Alert, task: Task, alarmViewModel: AlarmViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = alert.toString())
        Text(text = task.toString())
        Button(onClick = {
            alarmViewModel.createDelayedOneTimeAlert(
                alert,
                task,
                Duration.of(5, ChronoUnit.MINUTES).toMillis()
            )
        }) {
            Text(text = "Delay by 5 minutes")
        }
    }
}