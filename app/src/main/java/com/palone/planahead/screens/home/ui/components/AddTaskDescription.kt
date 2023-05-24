package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddTaskDescription(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(text = "Description:") // TODO add this to res strings
        TextField(value = value, onValueChange = onValueChange)
    }
}

@Preview
@Composable
fun PreviewAddTaskDescription() {
    AddTaskDescription(value = "test", onValueChange = {})
}