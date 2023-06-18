package com.palone.planahead

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.screens.taskEdit.TaskEditViewModel
import com.palone.planahead.services.alarms.AlarmsHandler
import com.palone.planahead.ui.theme.PlanAheadTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var alarmsHandler: AlarmsHandler


    @Inject
    lateinit var taskRepository: TaskRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanAheadTheme {
                val taskEditScreenViewModel = hiltViewModel<TaskEditViewModel>()
                val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()

                val t = taskRepository.allTasksWithAlerts.collectAsState(initial = emptyList())
                if (t.value.isNotEmpty()) {
                    LaunchedEffect(t.value) {
                        alarmsHandler.createAlarmEntries(t.value)
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlanAheadApp(
                        homeScreenViewModel = homeScreenViewModel,
                        taskEditViewModel = taskEditScreenViewModel
                    )
                }
            }
        }
    }
}
