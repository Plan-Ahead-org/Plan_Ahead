package com.palone.planahead

import android.app.KeyguardManager
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.palone.planahead.data.database.TaskWithAlerts
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.services.alarms.AlarmsHandler
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlarmScreenActivity : ComponentActivity() {
    lateinit var ringtonePlayer: MediaPlayer

    @Inject
    lateinit var alarmsHandler: AlarmsHandler

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alert = intent?.getParcelableExtra<Alert>("alert")
        val task = intent?.getParcelableExtra<Task>("task")

        showScreenWhenPhoneLocked()
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val ringtoneSoundUri: Uri = Settings.System.DEFAULT_RINGTONE_URI
        ringtonePlayer = MediaPlayer()

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        ringtonePlayer.setAudioAttributes(audioAttributes)
        ringtonePlayer.isLooping = true
        ringtonePlayer.setDataSource(this, ringtoneSoundUri)
        ringtonePlayer.prepare()
        ringtonePlayer.start()

        setContent {
            MaterialTheme {
                Surface {
                    if ((task != null) && (alert != null)) {
                        ShowReminderUi(alert, task, alarmsHandler)
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ringtonePlayer.stop()
    }

    private fun showScreenWhenPhoneLocked() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            (getSystemService(KEYGUARD_SERVICE) as KeyguardManager).requestDismissKeyguard(
                this,
                null
            )
        }
    }
}

@Composable
fun ShowReminderUi(alert: Alert, task: Task, alarmsHandler: AlarmsHandler) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = alert.toString())
        Text(text = task.toString())
        Button(onClick = {
            createDelayedOneTimeAlert(
                alert,
                alarmsHandler,
                task,
                Duration.of(5, ChronoUnit.MINUTES).toMillis()
            )
        }) {
            Text(text = "Delay by 5 minute")
        }
    }
}

private fun createDelayedOneTimeAlert(
    alert: Alert,
    alarmsHandler: AlarmsHandler,
    task: Task,
    delay: Long
) {
    val currentEventMillisInEpoch = alert.eventMillisInEpoch ?: 0
    val newTask = task.copy(taskType = TaskType.ONE_TIME)
    val newAlert = alert.copy(eventMillisInEpoch = currentEventMillisInEpoch + delay)
    alarmsHandler.setAlarms(
        listOf(
            TaskWithAlerts(
                task = newTask,
                alerts = listOf(newAlert)
            )
        )
    )
}
