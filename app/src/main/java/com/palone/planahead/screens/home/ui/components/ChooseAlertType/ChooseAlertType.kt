package com.palone.planahead.screens.home.ui.components.ChooseAlertType

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.palone.planahead.data.database.alert.properties.AlertType

@Composable
fun ChooseAlertType(
    modifier: Modifier = Modifier,
    checkedAlertTypes: List<AlertType>,
    onValueChange: (List<AlertType>) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AlertTypeCheckbox(
            modifier = Modifier.weight(0.3f),
            checkedAlertTypes = checkedAlertTypes,
            onValueChange = onValueChange,
            alertType = AlertType.ALARM
        )
        AlertTypeCheckbox(
            modifier = Modifier.weight(0.3f),
            checkedAlertTypes = checkedAlertTypes,
            onValueChange = onValueChange,
            alertType = AlertType.NOTIFICATION
        )
        AlertTypeCheckbox(
            modifier = Modifier.weight(0.3f),
            checkedAlertTypes = checkedAlertTypes,
            onValueChange = onValueChange,
            alertType = AlertType.PERSISTENT_NOTIFICATION
        )

    }
}

@Composable
@Preview
fun PreviewChooseAlertType() {
    ChooseAlertType(modifier = Modifier.fillMaxWidth(),
        checkedAlertTypes = listOf(AlertType.ALARM, AlertType.PERSISTENT_NOTIFICATION),
        onValueChange = {})
}

