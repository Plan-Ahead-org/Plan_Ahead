package com.palone.planahead.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.planahead.data.HomeScreenUIState
import com.palone.planahead.data.TaskBuilder
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.task.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow() // TODO kiedyś się przyda
    val allTasks = taskRepository.allTasks
    private val taskBuilder = TaskBuilder()
    fun upsertTask(task: Task) {
        viewModelScope.launch {
            _uiState.update { _uiState.value.copy(isLoading = true) }
            taskRepository.upsert(task)
            _uiState.update { _uiState.value.copy(isLoading = false) }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _uiState.update { _uiState.value.copy(isLoading = true) }
            taskRepository.delete(task)
            _uiState.update { _uiState.value.copy(isLoading = false) }
        }
    }

    fun createTask( // TODO w teorii zamiast tego mockTask z UI
        task: Task,
        callback: (Task) -> Unit
    ) {
        viewModelScope.launch {
            taskBuilder.clearTask()
            taskBuilder.updateTask(
                task
            )
            callback(taskBuilder.getTask())
            taskBuilder.clearTask()
        }
    }


    init {

    }
}