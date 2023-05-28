package com.palone.planahead.screens.home.ui.components.chooseAlertTypes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.palone.planahead.data.database.alert.properties.AlertType

@Composable
fun AlertTypeCheckbox(
    modifier: Modifier = Modifier,
    alertType: AlertType,
    isChecked: Boolean,
    onValueChange: (AlertType) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = alertType.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onValueChange(alertType) })
    }
}