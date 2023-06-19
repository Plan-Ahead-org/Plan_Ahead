package com.palone.planahead

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.services.alarms.AlarmsHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MarkAsDoneService : Service() {
    private val scope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var taskRepository: TaskRepository

    @Inject
    lateinit var alarmsHandler: AlarmsHandler
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            setTaskAsDone(intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setTaskAsDone(intent: Intent) {
        val task = intent.getParcelableExtra<Task>("task")
        Log.i("MarkAsDoneActivity", "called... :), $task")
        if (task != null) {
            scope.launch {
                taskRepository.upsert(task.copy(isCompleted = true))
                alarmsHandler.setAlarms(taskRepository.allTasksWithAlerts.last()) // load changes
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}