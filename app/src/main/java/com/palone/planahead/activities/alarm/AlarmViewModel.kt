package com.palone.planahead.activities.alarm

import androidx.lifecycle.ViewModel
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.domain.alertEngine.AlertEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(private val alertEngine: AlertEngine) : ViewModel() {

    fun createDelayedOneTimeAlert(
        alert: Alert,
        task: Task,
        delay: Long
    ) {
        alertEngine.createDelayedOneTimeAlert(alert, task, delay)
    }

}