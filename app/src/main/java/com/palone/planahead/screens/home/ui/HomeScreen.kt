package com.palone.planahead.screens.home.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.palone.planahead.data.ScreensProperties
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.screens.home.ui.components.FloatingActionButtonAddTask
import com.palone.planahead.screens.home.ui.components.TaskItem

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navHostController: NavHostController) {
    val uiState = viewModel.uiState.collectAsState().value
    val uiScope = rememberCoroutineScope()
    val tasks =
        viewModel.uiState.collectAsState().value.allTasks.collectAsState(initial = listOf())
    if (uiState.showEditTaskScreen) {
        navHostController.navigate(ScreensProperties.TaskEditScreen.route)
        viewModel.shouldShowTaskEditScreen(false)
    }
    Scaffold(floatingActionButton = {
        FloatingActionButtonAddTask(onClick = {
            viewModel.shouldShowTaskEditScreen(true)
        })
    }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            tasks.value.forEach { (task, alerts) ->
                item {
                    TaskItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        task = task,
                        alerts = alerts,
                        onDelete = { viewModel.deleteTask(it) }
                    )
                }
            }
        }
    }
}
