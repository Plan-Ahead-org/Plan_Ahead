package com.palone.planahead.screens.taskEdit.ui.selectors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.screens.taskEdit.ui.scroller.Scroller
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    todayDateAndTime: LocalDateTime,
    selectedHour: Int,
    onNewValue: (time: LocalTime) -> Unit,
    selectedMinute: Int
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        fun fontWeight(isSelected: Boolean) = if (isSelected) FontWeight.Bold else FontWeight.Normal
        fun fontSize(isSelected: Boolean) = if (isSelected) 25.sp else 20.sp

        Scroller(
            modifier = Modifier
                .height(200.dp)
                .weight(1f),
            items = (0..23).toList().toTypedArray(),
            selectedItem = todayDateAndTime.hour,
            onNewSelection = {
                onNewValue(LocalTime.of(it.toInt(), selectedMinute))
            }) { item, isSelected ->
            Text(
                modifier = Modifier.padding(20.dp),
                text = "$item",
                fontWeight = fontWeight(isSelected),
                fontSize = fontSize(isSelected)
            )
        }

        Scroller(
            modifier = Modifier
                .height(200.dp)
                .weight(1f),
            items = (0..59).toList().toTypedArray(),
            selectedItem = todayDateAndTime.minute,
            onNewSelection = {
                onNewValue(LocalTime.of(selectedHour, it.toInt()))
            }) { item, isSelected ->
            Text(
                modifier = Modifier.padding(20.dp),
                text = "$item",
                fontWeight = fontWeight(isSelected),
                fontSize = fontSize(isSelected)
            )
        }
    }
}