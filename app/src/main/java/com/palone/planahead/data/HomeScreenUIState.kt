package com.palone.planahead.data

import com.palone.planahead.data.database.TaskWithAlerts
import kotlinx.coroutines.flow.Flow


data class HomeScreenUIState(
    var isLoading: Boolean = false,
    var shouldShowDrawer: Boolean = false,
    val mockTaskProperties: MockTaskProperties = MockTaskProperties(),
    val allTasks: Flow<List<TaskWithAlerts>>
)