package com.palone.planahead.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.domain.taskEngine.TaskEngine
import com.palone.planahead.screens.home.data.HomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alertRepository: AlertRepository,
    private val taskEngine: TaskEngine
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(HomeScreenUIState(allTasks = taskRepository.allTasksWithAlerts))
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskEngine.completeTask(task)
        }
    }


    fun shouldShowTaskEditScreen(value: Boolean) {
        _uiState.update { _uiState.value.copy(showEditTaskScreen = value) }
    }

    init {}
}