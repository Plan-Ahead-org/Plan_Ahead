package com.palone.planahead.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.screens.home.data.HomeScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HomeScreenViewModel(
    private val taskRepository: TaskRepository,
    private val alertRepository: AlertRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(HomeScreenUIState(allTasks = taskRepository.allTasksWithAlerts))
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    private fun combineAlertWithDateTime(
        dateTime: LocalDateTime,
        alertTrigger: AlertTrigger,
        alertType: AlertType,
        interval: Long?
    ): List<Alert> {
        val alerts = mutableListOf<Alert>()
        val zoneId = ZoneId.systemDefault()
        val instant = dateTime.atZone(zoneId).toInstant()
        val epochMillis = instant.toEpochMilli()
        alerts.add(
            Alert(
                alertTriggerName = alertTrigger,
                alertTypeName = alertType,
                eventMillisInEpoch = epochMillis,
                interval = interval,
            )
        )
        return alerts
    }

    fun createDatabaseEntry(
        description: String,
        taskType: TaskType,
        alertTypes: List<AlertType>,
        alertTriggers: List<AlertTrigger>,
        eventMillisInEpoch: Long?,
        interval: Long?,
        selectedMultipleTimes: List<LocalDateTime>?,
        timeBeforeDeadlineAlert: List<Long>?,
        deadline: Long?,
        hasDeadline: Boolean
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
                    if (!selectedMultipleTimes.isNullOrEmpty()) {
                        alerts.addAll(
                            combineCron(
                                selectedMultipleTimes,
                                alertTrigger,
                                alertType,
                                interval
                            )
                        )
                    } else if (timeBeforeDeadlineAlert != null && deadline != null) { // ONE_TIME
                        alerts.addAll(
                            combineOneTime(
                                timeBeforeDeadlineAlert,
                                alertTrigger,
                                alertType,
                                deadline,
                                interval
                            )
                        )
                    } else {
                        alerts.add(
                            Alert( // CHORE
                                alertTriggerName = alertTrigger,
                                alertTypeName = alertType,
                                eventMillisInEpoch = eventMillisInEpoch,
                                interval = interval,
                            )
                        )
                    }

                }
            }
            val id = taskRepository.upsert(mainTask) // send task to database
            alerts.forEach {
                alertRepository.upsert(it.copy(taskId = id.toInt())) // send alert to database
            }
            _uiState.update { _uiState.value.copy(isLoading = false) }
        }
    }

    private fun combineCron(
        selectedMultipleTimes: List<LocalDateTime>,
        alertTrigger: AlertTrigger,
        alertType: AlertType,
        interval: Long?
    ): List<Alert> {
        val alerts = mutableListOf<Alert>()
        selectedMultipleTimes.forEach {//CRON
            alerts.addAll(
                combineAlertWithDateTime(
                    dateTime = it,
                    alertTrigger = alertTrigger,
                    alertType = alertType,
                    interval = interval
                )
            )
        }
        return alerts.toList()
    }

    private fun combineOneTime(
        timeBeforeDeadlineAlert: List<Long>,
        alertTrigger: AlertTrigger,
        alertType: AlertType,
        deadline: Long,
        interval: Long?
    ): List<Alert> {
        val alerts = mutableListOf<Alert>()
        timeBeforeDeadlineAlert.forEach {
            alerts.add( // ONE_TIME
                Alert(
                    alertTriggerName = alertTrigger,
                    alertTypeName = alertType,
                    eventMillisInEpoch = deadline.minus(it),
                    interval = interval,
                )
            )
        }
        return alerts.toList()
    }

    fun shouldShowTaskEditScreen(value: Boolean) {
        _uiState.update { _uiState.value.copy(shouldShowEditTaskScreen = value) }
    }

    init {}
}