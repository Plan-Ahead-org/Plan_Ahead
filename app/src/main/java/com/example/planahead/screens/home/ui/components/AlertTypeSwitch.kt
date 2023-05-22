package com.example.planahead.screens.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planahead.data.task.properties.TaskType

@Composable
fun AlertTypeSwitch(modifier: Modifier = Modifier, selected: String, onSwitch: (String) -> Unit) {
    val choreColor = remember { mutableStateOf(Color.Black) }
    val cronColor = remember { mutableStateOf(Color.Black) }
    val oneTimeColor = remember { mutableStateOf(Color.Black) }

    when (selected) {
        TaskType.CHORE -> {
            choreColor.value = Color.Gray
            cronColor.value = Color.Black
            oneTimeColor.value = Color.Black
        }

        TaskType.CRON -> {
            choreColor.value = Color.Black
            cronColor.value = Color.Gray
            oneTimeColor.value = Color.Black
        }

        TaskType.ONE_TIME -> {
            choreColor.value = Color.Black
            cronColor.value = Color.Black
            oneTimeColor.value = Color.Gray
        }
    }
    Row(modifier = modifier) {
        Card(
            colors = CardDefaults.cardColors(containerColor = oneTimeColor.value),
            modifier = Modifier
                .weight(1f)
                .clickable { onSwitch(TaskType.ONE_TIME) },
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 10.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = TaskType.ONE_TIME)
            }
        }
        Card(
            colors = CardDefaults.cardColors(containerColor = choreColor.value),
            modifier = Modifier
                .weight(1f)
                .clickable { onSwitch(TaskType.CHORE) },
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = TaskType.CHORE)
            }
        }
        Card(
            colors = CardDefaults.cardColors(containerColor = cronColor.value),
            modifier = Modifier
                .weight(1f)
                .clickable { onSwitch(TaskType.CRON) },
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 10.dp,
                bottomEnd = 10.dp,
                bottomStart = 0.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = TaskType.CRON)
            }
        }
    }

}