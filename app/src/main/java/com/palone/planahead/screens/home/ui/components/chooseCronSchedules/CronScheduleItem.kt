package com.palone.planahead.screens.home.ui.components.chooseCronSchedules

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.palone.planahead.data.CronScheduleDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CronScheduleItem(schedule: CronScheduleDateTime, onClick: () -> Unit) {
    Text(
        text = schedule.date.format(DateTimeFormatter.ISO_LOCAL_DATE),
        fontWeight = FontWeight.Bold
    )
    schedule.time.forEach { time -> Text(text = "  " + time.format(DateTimeFormatter.ISO_LOCAL_TIME)) }
    Text(text = "Add time trigger", modifier = Modifier.clickable {
        onClick()
    })
}