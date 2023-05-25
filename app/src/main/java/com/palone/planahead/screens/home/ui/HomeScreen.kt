package com.palone.planahead.screens.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.TaskType
import com.palone.planahead.data.database.task.properties.TaskPriority
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.screens.home.ui.components.AddTaskDescription
import com.palone.planahead.screens.home.ui.components.ChooseAlertType.ChooseAlertType
import com.palone.planahead.screens.home.ui.components.ChooseOneTimeEventDate
import com.palone.planahead.screens.home.ui.components.ChooseTaskPriority
import com.palone.planahead.screens.home.ui.components.ChooseTaskType.ChooseTaskType
import com.palone.planahead.screens.home.ui.components.FloatingActionButtonAddTask
import com.palone.planahead.screens.home.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navHostController: NavHostController) {
    val uiState = viewModel.uiState.collectAsState().value
    val sheetState = rememberModalBottomSheetState()
    val uiScope = rememberCoroutineScope()
    val tasks =
        viewModel.uiState.collectAsState().value.allTasks.collectAsState(initial = listOf()) // not sure about this one (it looks awful)
    if (viewModel.uiState.collectAsState().value.shouldShowDrawer)
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { viewModel.hideShowBottomSheet(sheetState, uiScope) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddTaskDescription(
                    value = uiState.mockTaskDescription,
                    onValueChange = { viewModel.updateMockTaskDescription(it) })
                ChooseTaskPriority(
                    selectedPriority = TaskPriority.LOW,
                    onValueChange = { taskPriority -> viewModel.updateMockTaskPriority(taskPriority) })
                ChooseAlertType(modifier = Modifier.fillMaxWidth(),
                    checkedAlertTypes = uiState.mockAlertTypes,
                    onValueChange = { viewModel.updateMockTaskAlertTypes(it) })
                ChooseTaskType(
                    selectedTaskType = uiState.mockAlertTaskType,
                    onValueChange = { viewModel.updateMockTaskType(it) })
                if (uiState.mockAlertTaskType == TaskType.ONE_TIME)
                    ChooseOneTimeEventDate(onValueChange = { viewModel.updateMockTaskData(it) })
                Button(onClick = {
                    viewModel.createDatabaseEntry(
                        uiState.mockTaskDescription,
                        uiState.mockAlertTypes,
                        listOf(AlertTrigger.TIME),
                        uiState.mockAlertTaskData
                    )
                }) {
                    Text(text = "Add") // TODO add this to string res
                }
            }
        }

    Scaffold(floatingActionButton = {
        FloatingActionButtonAddTask(onClick = {
            viewModel.showBottomSheet(sheetState, uiScope)
        })

    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            tasks.value.forEach { (task, alerts) ->
                item {
                    TaskItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp), task = task, alerts = alerts
                    )
                }
            }
        }
    }
}
