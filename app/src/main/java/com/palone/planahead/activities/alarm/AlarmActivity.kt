package com.palone.planahead.activities.alarm

import android.app.KeyguardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.palone.planahead.activities.alarm.ui.AlarmScreen
import com.palone.planahead.data.database.alert.Alert
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.services.ringtone.RingtoneHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {

    @Inject
    lateinit var ringtoneHandler: RingtoneHandler

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alert = intent?.getParcelableExtra<Alert>("alert")
        val task = intent?.getParcelableExtra<Task>("task")

        showScreenWhenPhoneLocked()
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        ringtoneHandler.start(this)

        setContent {
            val alarmViewModel = hiltViewModel<AlarmViewModel>()
            MaterialTheme {
                Surface {
                    if ((task != null) && (alert != null)) {
                        AlarmScreen(alert, task, alarmViewModel)
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ringtoneHandler.stop()
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
