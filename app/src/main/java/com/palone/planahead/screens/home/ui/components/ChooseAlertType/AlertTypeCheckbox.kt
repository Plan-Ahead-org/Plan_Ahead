package com.palone.planahead.screens.home.ui.components.ChooseAlertType

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.toggle

@Composable
fun AlertTypeCheckbox(
    modifier: Modifier = Modifier,
    alertType: AlertType,
    checkedAlertTypes: List<AlertType>,
    onValueChange: (List<AlertType>) -> Unit
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
            checked = checkedAlertTypes.contains(alertType),
            onCheckedChange = { onValueChange(checkedAlertTypes.toggle(alertType)) })
    }
}