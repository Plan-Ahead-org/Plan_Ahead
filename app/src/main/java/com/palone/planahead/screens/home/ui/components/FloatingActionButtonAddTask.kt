package com.palone.planahead.screens.home.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FloatingActionButtonAddTask(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        text = { Text(text = "Add task") },
        icon = {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Task"
            )
        },//TODO Add as string resource
        onClick = onClick
    )
}

@Preview
@Composable
fun Preview() {
    FloatingActionButtonAddTask {}
}