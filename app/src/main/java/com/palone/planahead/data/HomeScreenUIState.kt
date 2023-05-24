package com.palone.planahead.data

import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.alert.properties.TaskType


data class HomeScreenUIState(
    var isLoading: Boolean = false,
    var shouldShowDrawer: Boolean = false,
    var mockTaskDescription: String = "",
    var mockAlertTriggers: List<AlertTrigger> = emptyList(),
    var mockAlertTypes: List<AlertType> = emptyList(),
    val mockAlertTaskType: TaskType = TaskType.ONE_TIME,
    val mockAlertTaskData: String = ""
)