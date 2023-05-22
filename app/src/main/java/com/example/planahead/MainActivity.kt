package com.example.planahead

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.planahead.data.database.TaskDatabase
import com.example.planahead.data.database.TaskRepository
import com.example.planahead.screens.home.HomeScreenViewModel
import com.example.planahead.ui.theme.PlanAheadTheme

class MainActivity : ComponentActivity() {
    private val db by lazy { Room.databaseBuilder(applicationContext,TaskDatabase::class.java,"tasks.db").build() }
    private val taskRepository by lazy { TaskRepository(db.dao)  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeScreenViewModel by viewModels<HomeScreenViewModel>(factoryProducer = {
            object :ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeScreenViewModel(taskRepository) as T
                }
            }
        })
        setContent {
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
