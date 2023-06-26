package com.palone.planahead.screens.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.palone.planahead.data.ScreensProperties
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.screens.home.ui.components.FloatingActionButtonAddTask
import com.palone.planahead.screens.home.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navHostController: NavHostController) {
    val uiState = viewModel.uiState.collectAsState().value
    val tasks =
        viewModel.uiState.collectAsState().value.allTasks.collectAsState(initial = listOf())
    val listState = rememberLazyListState()
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
        LazyColumn(state = listState, modifier = Modifier.padding(padding)) {
            items(
                items = tasks.value.filter { !it.task.isCompleted },
                key = { item -> item.task }) { taskWithAlerts ->
                val currentItem = rememberUpdatedState(newValue = taskWithAlerts)
                val swipeOffset = remember {
                    mutableStateOf(0f)
                }
                val dismissState = rememberDismissState(confirmValueChange = {
                    swipeOffset.value > 0.3f
                })
                LaunchedEffect(dismissState.progress) {
                    swipeOffset.value = dismissState.progress
                    if (dismissState.progress > 0.999f && dismissState.progress != 1f)
                        viewModel.deleteTask(currentItem.value.task)
                }
                SwipeToDismiss(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    state = dismissState,
                    background = {
                        SwipeToDismissBackground()
                    },
                    dismissContent = {
                        TaskItem(
                            task = currentItem.value.task,
                            alerts = currentItem.value.alerts,
                        )
                    })
            }
        }
    }
}

@Composable
private fun SwipeToDismissBackground() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(13.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Done",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
    }
}

