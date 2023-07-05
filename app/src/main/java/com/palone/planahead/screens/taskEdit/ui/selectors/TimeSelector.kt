package com.palone.planahead.screens.taskEdit.ui.selectors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.screens.taskEdit.ui.scroller.Scroller
import java.time.LocalTime

@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime,
    onNewValue: (time: LocalTime) -> Unit,
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    Row(modifier = modifier.drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.verticalGradient(
                listOf(
                    backgroundColor, Color.Transparent, backgroundColor
                )
            )
        )
    }, horizontalArrangement = Arrangement.spacedBy(0.dp)) {
        fun fontWeight(isSelected: Boolean) = if (isSelected) FontWeight.Bold else FontWeight.Normal
        fun fontSize(isSelected: Boolean) = if (isSelected) 25.sp else 20.sp

        Scroller(
            modifier = Modifier
                .height(200.dp)
                .weight(1f),
            items = (0..23).toList().toTypedArray(),
            selectedItem = selectedTime.hour,
            onNewSelection = {
                onNewValue(LocalTime.of(it.toInt(), selectedTime.minute))
            }) { item, isSelected ->
            Text(
                modifier = Modifier.padding(vertical = 20.dp),
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
            selectedItem = selectedTime.minute,
            onNewSelection = {
                onNewValue(LocalTime.of(selectedTime.hour, it.toInt()))
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