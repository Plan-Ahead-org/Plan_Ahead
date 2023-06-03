package com.palone.planahead.screens.home.ui.components.ChooseOneTimeEventDate

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerDialog(
    onDismissRequest: () -> Unit,
    onClickRequest: () -> Unit,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onClickRequest
            ) {
                Text(text = "Ok")
            }
        }) {
        content()
    }
}