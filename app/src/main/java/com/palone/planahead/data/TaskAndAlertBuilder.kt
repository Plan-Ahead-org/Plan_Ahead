package com.palone.planahead.data

import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task

class TaskAndAlertBuilder(
    private val taskRepository: TaskRepository,
    private val alertRepository: AlertRepository
) {
    //TODO make an Interface of this class
    private var _task = Task(taskId = null, description = "", addedDate = "", isCompleted = false)
    private var _alerts: MutableList<Alert> = mutableListOf()

    fun updateTask(task: Task): Task {
        _task = task
        return _task
    }

    fun setDefaultTask(): Task {
        _task = Task(taskId = null, description = "", addedDate = "", isCompleted = false)
        return _task
    }

    fun addAlert(alertType: AlertType, alertTrigger: AlertTrigger, alertTriggerData: String) {
        _alerts.add(
            Alert(
                taskId = null,
                alertId = null,
                alertTypeName = alertType,
                alertTriggerName = alertTrigger,
                alertTriggerData = alertTriggerData
            )
        )
    }

    suspend fun sendToDatabase() {
        val id = taskRepository.upsert(_task)
        _alerts.forEach {
            alertRepository.upsert(it.copy(taskId = id.toInt()))
        }
    }

}