package com.palone.planahead.screens.taskEdit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.palone.planahead.data.ScreensProperties
import com.palone.planahead.screens.home.ui.components.FloatingActionButtonAddTask
import com.palone.planahead.screens.taskEdit.TaskEditViewModel
import com.palone.planahead.screens.taskEdit.data.AlertProperty
import com.palone.planahead.screens.taskEdit.ui.notificationSelection.AlertCreator
import com.palone.planahead.screens.taskEdit.ui.notificationSelection.AlertSelection
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.TaskTypeSection
import java.time.LocalTime

@Composable
fun TaskEditScreen(viewModel: TaskEditViewModel, navHostController: NavHostController) {
    val addTaskScrollState = rememberScrollState()
    val taskName = remember { mutableStateOf("") }
    val shouldShowAddAlertDialog = remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Scaffold(floatingActionButton = {
            if (viewModel.alertProperties.collectAsState().value.isNotEmpty()) {
                FloatingActionButtonAddTask(modifier = Modifier.fillMaxWidth(0.7f)) {
                    viewModel.createDatabaseEntry(name = taskName.value)
                    viewModel.resetProperties()
                    navHostController.navigate(ScreensProperties.HomeScreen.route)
                }
            } else {
                Text(text = "Please add at least one alert")
            }

        }, floatingActionButtonPosition = FabPosition.Center) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = addTaskScrollState)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddTaskDescription(modifier = Modifier.fillMaxWidth(),
                    value = taskName.value,
                    onValueChange = { taskName.value = it })
                Spacer(modifier = Modifier.height(9.dp))
                TaskTypeSection(viewModel = viewModel)
                Spacer(modifier = Modifier.height(9.dp))
                AlertSelection(viewModel = viewModel)
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Button(onClick = { shouldShowAddAlertDialog.value = true }) {
                        Icon(Icons.Default.Add, null)
                        Text(text = "add reminder")
                    }
                }
                if (shouldShowAddAlertDialog.value) {
                    AddAlertDialog(
                        onNewAlert = { alert ->
                            viewModel.insertAlertProperty(alert)
                            shouldShowAddAlertDialog.value = false
                        },
                        onDismissRequest = { shouldShowAddAlertDialog.value = false })
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }

}

@Composable
fun AddAlertDialog(onNewAlert: (AlertProperty) -> Unit, onDismissRequest: () -> Unit) {
    val alert = remember {
        mutableStateOf(AlertProperty(offsetTime = LocalTime.of(0, 10)))
    }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.95f)) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.outline
                    )
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.background(Color.Transparent)
            ) {
                AlertCreator(
                    alert = alert.value,
                    onValueChange = { alertProperty -> alert.value = alertProperty })

                BottomButtons(onDismissRequest, onNewAlert, alert)
            }
        }
    }
}

@Composable
private fun BottomButtons(
    onDismissRequest: () -> Unit,
    onNewAlert: (AlertProperty) -> Unit,
    alert: MutableState<AlertProperty>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "cancel",
            modifier = Modifier
                .clickable { onDismissRequest() }
        )
        Button(
            onClick = { onNewAlert(alert.value) },
        ) {
            Icon(Icons.Default.Add, null)
            Text(text = "add reminder")
        }
    }
}