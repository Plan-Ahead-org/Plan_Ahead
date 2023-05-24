package com.palone.planahead.screens.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.flip

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
        Column(
            modifier = Modifier.weight(0.3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {//ALARM
            Text(text = AlertType.ALARM.name)
            Checkbox(
                checked = checkedAlertTypes.contains(AlertType.ALARM),
                onCheckedChange = { onValueChange(checkedAlertTypes.flip(AlertType.ALARM)) })
        }
        Column(
            modifier = Modifier.weight(0.3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {//NOTIFICATION
            Text(text = AlertType.NOTIFICATION.name)
            Checkbox(
                checked = checkedAlertTypes.contains(AlertType.NOTIFICATION),
                onCheckedChange = { onValueChange(checkedAlertTypes.flip(AlertType.NOTIFICATION)) })
        }
        Column(
            modifier = Modifier.weight(0.3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {//PERSISTENT NOTIFICATION
            Text(
                text = AlertType.PERSISTENT_NOTIFICATION.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Checkbox(
                checked = checkedAlertTypes.contains(AlertType.PERSISTENT_NOTIFICATION),
                onCheckedChange = { onValueChange(checkedAlertTypes.flip(AlertType.PERSISTENT_NOTIFICATION)) })
        }

    }
}

@Composable
@Preview
fun PreviewChooseAlertType() {
    ChooseAlertType(modifier = Modifier.fillMaxWidth(),
        checkedAlertTypes = listOf(AlertType.ALARM, AlertType.PERSISTENT_NOTIFICATION),
        onValueChange = {})
}

