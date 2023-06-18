package com.palone.planahead.screens.home.data

import com.palone.planahead.data.database.TaskWithAlerts
import kotlinx.coroutines.flow.Flow


data class HomeScreenUIState(
    var isLoading: Boolean = false,
    var showEditTaskScreen: Boolean = false,
    val mockTaskProperties: MockTaskProperties = MockTaskProperties(),
    val allTasks: Flow<List<TaskWithAlerts>>
)