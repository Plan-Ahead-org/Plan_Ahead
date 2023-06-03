package com.palone.planahead.screens.home.ui.components.chooseChoreTimes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
import java.time.temporal.ChronoUnit

@Composable
fun ChooseChoreInterval(modifier: Modifier = Modifier, onValueChange: (Long) -> Unit) {
    val textFieldValue = remember { mutableStateOf("") }
    val selectedTimeUnit = remember { mutableStateOf(ChronoUnit.HOURS) }
    val scrollState = rememberScrollState()
    Row(modifier = modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(0.3f),
            value = textFieldValue.value,
            onValueChange = { value: String ->
                textFieldValue.value = value
                if (textFieldValue.value.isNotEmpty())
                    onValueChange(
                        Duration.of(textFieldValue.value.toLong(), selectedTimeUnit.value)
                            .toMillis()
                    )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Duration.of(1, ChronoUnit.HOURS).toMillis()
        Column {

            Column(
                modifier = Modifier
                    .height(75.dp)
                    .verticalScroll(state = scrollState)
            ) {
                ChronoUnit.values().forEach {
                    Text(
                        text = it.name,
                        modifier = Modifier.clickable { selectedTimeUnit.value = it },
                        fontWeight = FontWeight(if (selectedTimeUnit.value == it) 700 else 1) // TODO change it, just for tests
                    )
                }
            }
            Text(text = "Scroll and select ^", fontSize = 10.sp)
        }
    }
}