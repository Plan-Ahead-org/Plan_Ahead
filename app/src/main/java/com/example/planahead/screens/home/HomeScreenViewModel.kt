package com.example.planahead.screens.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planahead.data.HomeScreenUIState
import com.example.planahead.data.TaskBuilder
import com.example.planahead.data.database.TaskRepository
import com.example.planahead.data.task.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun createTask( // TODO w teorii zamiast tego nichTask z UI
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