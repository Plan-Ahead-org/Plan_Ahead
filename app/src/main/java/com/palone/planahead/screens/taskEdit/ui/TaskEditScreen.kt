package com.palone.planahead.screens.taskEdit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.palone.planahead.data.ScreensProperties
import com.palone.planahead.screens.home.ui.components.FloatingActionButtonAddTask
import com.palone.planahead.screens.taskEdit.TaskEditViewModel
import com.palone.planahead.screens.taskEdit.ui.notificationSelection.NotificationCreator
import com.palone.planahead.screens.taskEdit.ui.notificationSelection.NotificationSelection
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.TaskTypeSection

@Composable
fun TaskEditScreen(viewModel: TaskEditViewModel, navHostController: NavHostController) {
    val addTaskScrollState = rememberScrollState()
    val taskName = remember { mutableStateOf("") }
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Scaffold(floatingActionButton = {
            FloatingActionButtonAddTask(modifier = Modifier.fillMaxWidth(0.7f)) {
                viewModel.createDatabaseEntry(name = taskName.value)
                viewModel.resetProperties()
                navHostController.navigate(ScreensProperties.HomeScreen.route)
            }
        }, floatingActionButtonPosition = FabPosition.Center) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = addTaskScrollState)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddTaskDescription(
                    value = taskName.value,
                    onValueChange = { taskName.value = it })
                Spacer(modifier = Modifier.height(9.dp))
                TaskTypeSection(viewModel = viewModel)
                Spacer(modifier = Modifier.height(9.dp))
                NotificationSelection(viewModel = viewModel)
                Spacer(modifier = Modifier.padding(9.dp))
                NotificationCreator(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onNotificationCreation = { viewModel.insertAlertProperty(it) })
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }

}