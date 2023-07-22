package com.palone.planahead.screens.taskEdit.ui.selectors

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.screens.taskEdit.ui.scroller.Scroller
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.getEpochMillisFromLocalDateTime
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.getLocalDateTimeFromEpochMillis
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.getYearDays
import java.time.LocalDateTime

@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    todayDateAndTime: LocalDateTime,
    onNewValue: (dateTime: LocalDateTime) -> Unit,
) {
    fun fontWeight(isSelected: Boolean) = if (isSelected) FontWeight.Bold else FontWeight.Normal
    fun fontSize(isSelected: Boolean) = if (isSelected) 21.sp else 19.sp
    Scroller(
        modifier = modifier
            .height(200.dp),
        items = getYearDays(todayDateAndTime.year).toTypedArray(),
        selectedItem = getEpochMillisFromLocalDateTime(
            todayDateAndTime.withHour(0).withMinute(0).withSecond(0).withNano(0)
        ),
        onNewSelection = {
            onNewValue(
                getLocalDateTimeFromEpochMillis(it.toLong())
            )
        }) { item, isSelected ->
        if (item != 0) {
            val month = getLocalDateTimeFromEpochMillis(item.toLong()).month
            val day = getLocalDateTimeFromEpochMillis(item.toLong()).dayOfMonth
            val dayName = getLocalDateTimeFromEpochMillis(item.toLong()).dayOfWeek.name
            Text(
                modifier = Modifier.padding(20.dp),
                text = "${
                    dayName.take(3).lowercase()
                        .replaceFirstChar { it.titlecase() }
                }, ${
                    month.name.take(3).lowercase()
                        .replaceFirstChar { it.titlecase() }
                } $day",
                fontWeight = fontWeight(isSelected),
                fontSize = fontSize(isSelected)
            )
        } else {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "",
                fontSize = 20.sp
            )
        }

    }
}