package com.palone.planahead.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.planahead.data.HomeScreenUIState
import com.palone.planahead.data.TaskAndAlertBuilder
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.alert.properties.TaskType
import com.palone.planahead.data.database.task.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeScreenViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow() // TODO kiedyś się przyda
    val allTasks = taskRepository.allTasks
    private val taskAndAlertBuilder = TaskAndAlertBuilder(taskRepository)
    fun updateMockTaskDescription(description: String) {
        _uiState.update { _uiState.value.copy(mockTaskDescription = description) }
    }

    fun updateMockTaskAlertTypes(alertTypes: List<AlertType>) {
        _uiState.update { _uiState.value.copy(mockAlertTypes = alertTypes) }
    }

    fun updateMockTaskData(data: String) {
        _uiState.update { _uiState.value.copy(mockAlertTaskData = data) }
    }

    fun updateMockTaskType(taskType: TaskType) {
        _uiState.update { _uiState.value.copy(mockAlertTaskType = taskType) }
    }

    fun updateMockTaskAlertTriggers(alertTriggers: List<AlertTrigger>) {
        _uiState.update { _uiState.value.copy(mockAlertTriggers = alertTriggers) }
    }

    fun createDatabaseEntry(
        description: String,
        alertTypes: List<AlertType>,
        alertTriggers: List<AlertTrigger>,
        alertTriggerData: String
    ) {
        viewModelScope.launch {
            _uiState.update { _uiState.value.copy(isLoading = true) }
            val today = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            taskAndAlertBuilder.updateTask(Task(description = description, addedDate = today))
            alertTypes.forEach { alertType -> //TODO figure out better way to add lists to database
                alertTriggers.forEach { alertTrigger ->
                    taskAndAlertBuilder.addAlert(
                        alertType,
                        alertTrigger,
                        alertTriggerData
                    )
                }
            }
            taskAndAlertBuilder.sendToDataBase()
            taskAndAlertBuilder.setDefaultTask()
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


    init {

    }
}