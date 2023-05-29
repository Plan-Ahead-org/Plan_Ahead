package com.palone.planahead.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.planahead.data.HomeScreenUIState
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskPriority
import com.palone.planahead.data.database.task.properties.TaskType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeScreenViewModel(
    private val taskRepository: TaskRepository,
    private val alertRepository: AlertRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(HomeScreenUIState(allTasks = taskRepository.allTasksWithAlerts))
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    fun updateMockTaskDescription(description: String) {
        _uiState.update {
            _uiState.value.copy(
                mockTaskProperties = _uiState.value.mockTaskProperties.copy(
                    baseTask = _uiState.value.mockTaskProperties.baseTask.copy(description = description)
                )
            )
        }
    }

    fun updateMockAlertTypes(alertTypes: List<AlertType>) {
        _uiState.update {
            _uiState.value.copy(
                mockTaskProperties = _uiState.value.mockTaskProperties.copy(
                    alertTypes = alertTypes
                )
            )
        }
    }

    fun updateMockTaskType(taskType: TaskType) {
        _uiState.update {
            _uiState.value.copy(
                mockTaskProperties = _uiState.value.mockTaskProperties.copy(
                    baseTask = _uiState.value.mockTaskProperties.baseTask.copy(taskType = taskType)
                )
            )
        }
    }

    fun updateMockTaskAlertTriggers(alertTriggers: List<AlertTrigger>) {
        _uiState.update {
            _uiState.value.copy(
                mockTaskProperties = _uiState.value.mockTaskProperties.copy(
                    alertTriggers = alertTriggers
                )
            )
        }
    }

    fun updateMockTaskPriority(taskPriority: TaskPriority) {
        _uiState.update {
            _uiState.value.copy(
                mockTaskProperties = _uiState.value.mockTaskProperties.copy(
                    baseTask = _uiState.value.mockTaskProperties.baseTask.copy(priority = taskPriority) // wtf
                )
            )
        }
    }

    fun updateMockTaskEventMillisInEpoch(millis: Long) {//mockAlertEventMillisInEpoch
        _uiState.update {
            _uiState.value.copy(
                mockTaskProperties = _uiState.value.mockTaskProperties.copy(
                    alertEventMillisInEpoch = millis
                )
            )
        }
    }

    fun updateMockTaskInterval(interval: Long) {
        _uiState.update {
            _uiState.value.copy(
                mockTaskProperties = _uiState.value.mockTaskProperties.copy(
                    alertInterval = interval
                )
            )
        }
    }

    fun createDatabaseEntry(
        description: String,
        taskType: TaskType,
        alertTypes: List<AlertType>,
        alertTriggers: List<AlertTrigger>,
        eventMillisInEpoch: Long?,
        interval: Long?
    ) {
        val today = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val mainTask = Task(
            description = description,
            addedDate = today,
            taskType = taskType,
            isCompleted = false
        )
        val alerts: MutableList<Alert> = mutableListOf()
        viewModelScope.launch {
            _uiState.update { _uiState.value.copy(isLoading = true) }
            alertTypes.forEach { alertType ->

                alertTriggers.forEach { alertTrigger ->
                    alerts.add(
                        Alert(
                            alertTriggerName = alertTrigger,
                            alertTypeName = alertType,
                            eventMillisInEpoch = eventMillisInEpoch,
                            interval = interval
                        )
                    )
                }
            }
            val id = taskRepository.upsert(mainTask)
            alerts.forEach {
                alertRepository.upsert(it.copy(taskId = id.toInt()))
            }
            _uiState.update { _uiState.value.copy(isLoading = false) }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    fun showBottomSheet(sheetState: SheetState, uiScope: CoroutineScope) {
        uiScope.launch {
            _uiState.update { _uiState.value.copy(shouldShowDrawer = true) }
            sheetState.expand()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun hideShowBottomSheet(sheetState: SheetState, uiScope: CoroutineScope) {
        _uiState.update { _uiState.value.copy(shouldShowDrawer = false) }
        uiScope.launch { sheetState.hide() }
    }

    init {}
}