package com.palone.planahead

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
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
                        alarmsHandler.setAlarms(t.value)
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val notificationManager = NotificationManagerCompat.from(applicationContext)
                    if (notificationManager.areNotificationsEnabled()) {
                        PlanAheadApp(
                            homeScreenViewModel = homeScreenViewModel,
                            taskEditViewModel = taskEditScreenViewModel
                        )
                    } else {
                        AllowNotificationScreen()
                    }
                }
            }
        }
    }

    @Composable
    private fun AllowNotificationScreen() { // just to make testing easier, should be inside the app, but whatever
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = {})

            val color = remember { Animatable(Color.Red) }
            // Use LaunchedEffect to animate the color transitions indefinitely
            LaunchedEffect(Unit) {
                while (true) {
                    color.animateTo(Color.Red, animationSpec = tween(1000))
                    color.animateTo(Color.Cyan, animationSpec = tween(1000))
                    color.animateTo(Color.Yellow, animationSpec = tween(1000))
                }
            }
            val arrowColor = remember { Animatable(Color.Red) }
            // Use LaunchedEffect to animate the color transitions indefinitely
            LaunchedEffect(Unit) {
                while (true) {
                    arrowColor.animateTo(Color.Red, animationSpec = tween(200))
                    arrowColor.animateTo(
                        Color.Transparent,
                        animationSpec = tween(100)
                    )
                }
            }
            Text(
                text = "Why are push notifications important? They enhance your app experience by delivering timely information and keeping you informed about what matters most to you. Whether it's receiving notifications about time-sensitive sales, getting weather updates, or staying in the loop with news and events, push notifications ensure that you're always in the know.\n" +
                        "\n" +
                        "Rest assured, we respect your privacy. You can control your notification preferences at any time and choose the types of messages you receive. We value your trust and promise to only send you relevant and valuable information that enhances your app experience.\n" +
                        "\n" +
                        "To enable push notifications, simply tap 'Allow' when prompted. Don't worry, you can change your preferences later in the app settings if you ever wish to adjust the notification frequency or opt-out completely.\n" +
                        "\n" +
                        "Experience the full benefits of our app by allowing push notifications today. Stay connected, stay informed, and make the most of your app experience!"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "↑",
                    color = arrowColor.value,
                    fontSize = 100.sp,
                    modifier = Modifier.rotate(90f)
                )
                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = color.value)
                ) {
                    Text(text = "Allow Notifications")
                }
                Text(
                    text = "↑",
                    color = arrowColor.value,
                    fontSize = 100.sp,
                    modifier = Modifier.rotate(270f)
                )
            }
            Text(text = "Please restart the app after that :-)")
        }
    }
}
