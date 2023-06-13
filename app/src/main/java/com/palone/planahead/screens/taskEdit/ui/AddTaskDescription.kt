package com.palone.planahead.screens.taskEdit.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddTaskDescription(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        colors = TextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.background),
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Name:") })
}

@Preview
@Composable
fun PreviewAddTaskDescription() {
    AddTaskDescription(value = "test", onValueChange = {})
}