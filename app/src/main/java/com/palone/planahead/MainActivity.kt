package com.palone.planahead

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.PlanAheadDatabase
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.services.alarms.AlarmsHandler
import com.palone.planahead.ui.theme.PlanAheadTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PlanAheadDatabase::class.java,
            "tasks.db"
        ).build()
    }
    private val taskRepository by lazy { TaskRepository(db.taskDao) }
    private val alertRepository by lazy { AlertRepository(db.alertDao) }
    private val alarmManager: AlarmManager by lazy { getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    private val alarmsHandler by lazy { AlarmsHandler(applicationContext, alarmManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeScreenViewModel by viewModels<HomeScreenViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeScreenViewModel(taskRepository, alertRepository) as T
                }
            }
        })

        setContent {
            val t = taskRepository.allTasksWithAlerts.collectAsState(initial = emptyList())
            LaunchedEffect(t.value) {
                if (t.value.isNotEmpty())
                    alarmsHandler.createAlarmEntries(t.value)
            }
            PlanAheadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlanAheadApp(homeScreenViewModel = homeScreenViewModel)
                }
            }
        }
    }
}
