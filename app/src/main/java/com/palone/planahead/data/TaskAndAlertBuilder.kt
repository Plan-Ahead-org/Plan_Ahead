package com.palone.planahead.data

import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.alert.properties.AlertTrigger
import com.palone.planahead.data.database.alert.properties.AlertType
import com.palone.planahead.data.database.task.Task

class TaskAndAlertBuilder(private val repository: TaskRepository) {
    //TODO make an Interface of this class
    private var _task = Task(task_id = null, description = "", addedDate = "", isCompleted = false)
    private var _alerts: MutableList<Alert> = mutableListOf()

    fun updateTask(task: Task): Task {
        _task = task
        return _task
    }

    fun setDefaultTask(): Task {
        _task = Task(task_id = null, description = "", addedDate = "", isCompleted = false)
        return _task
    }

    fun addAlert(alertType: AlertType, alertTrigger: AlertTrigger, alertTriggerData: String) {
        _alerts.add(
            Alert(
                task_id = null,
                alert_id = null,
                alert_type_name = alertType,
                alert_trigger_name = alertTrigger,
                alert_trigger_data = alertTriggerData
            )
        )
    }

    suspend fun sendToDataBase() {
        val id = repository.upsertTask(_task)
        _alerts.forEach {
            repository.upsertAlert(it.copy(task_id = id.toInt()))
        }
    }

}