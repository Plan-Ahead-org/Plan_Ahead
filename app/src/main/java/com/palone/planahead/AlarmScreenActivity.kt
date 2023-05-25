package com.palone.planahead

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task

class AlarmScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alert = intent?.getParcelableExtra<Alert>("alert")
        val task = intent?.getParcelableExtra<Task>("task")
        setContent {
            MaterialTheme {
                Surface {
                    if ((task != null) && (alert != null)) {
                        ShowReminderUi(alert, task)
                    }
                }
            }
        }

    }
}

@Composable
fun ShowReminderUi(alert: Alert, task: Task) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = alert.toString())
        Text(text = task.toString())
    }
}
