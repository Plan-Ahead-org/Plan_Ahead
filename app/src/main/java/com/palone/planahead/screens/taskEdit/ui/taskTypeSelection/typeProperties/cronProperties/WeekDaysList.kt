package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.cronProperties


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.planahead.toggle
import java.time.DayOfWeek

@Composable
fun WeekDaysList(selectedDays: List<DayOfWeek>, onValueChange: (List<DayOfWeek>) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DayOfWeek.values().forEach {
            Day(
                modifier = Modifier.weight(1f),
                checked = selectedDays.contains(it),
                onCheckedChange = { _ -> onValueChange(selectedDays.toggle(it)) },
                field = it
            )
        }
    }
}

@Composable
fun Day(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    field: DayOfWeek
) {
    val cardColor =
        if (checked) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer) else CardDefaults.cardColors()
    Card(
        modifier = modifier
            .aspectRatio(1.5f)
            .padding(horizontal = 1.dp),
        shape = RoundedCornerShape(100.dp),
        colors = cardColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onCheckedChange(!checked) },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = field.name[0].toString() + field.name[1].toString().lowercase())
        }
    }
}