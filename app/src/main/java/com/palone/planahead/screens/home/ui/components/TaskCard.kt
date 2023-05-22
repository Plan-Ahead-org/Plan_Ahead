package com.palone.planahead.screens.home.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.planahead.data.task.Task
import com.palone.planahead.data.task.properties.AlertTrigger
import com.palone.planahead.data.task.properties.TaskType
import java.time.LocalTime

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    onValueChange: (Task) -> Unit = {},
    onDelete: (Task) -> Unit = {},
    onExpanded: (Int) -> Unit = {}
) {
    val shouldExpand = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .then(
                if (shouldExpand.value) Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                else Modifier
                    .clickable {
                        onExpanded(task.id!!)
                        shouldExpand.value = true
                    }
                    .width(180.dp)
                    .height(200.dp)
            )
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(top = 30.dp)
        ) {
            Text(
                text = task.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = task.alertTimeTrigger.toString(), fontSize = 10.sp)
            Text(
                text = "Added " + task.addedDate,
                fontSize = 10.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            if (shouldExpand.value) {
                Text(text = task.taskType)
                Text(text = task.alertTrigger)
                Text(
                    text = task.alertTypes.toString()
                        .replace("1", " Alarm ")
                        .replace("2", " Notification ")
                        .replace("3", " Persistent Notification ")
                        .replace("9", "Selected alert types: ")
                )
                Button(onClick = { shouldExpand.value = false }) {
                    Text(text = "Zatwierdź")
                }
                Button(onClick = {
                    onDelete(task)
                    shouldExpand.value = false
                }) {
                    Text(text = "Usuń")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun Prewiew() {
    TaskCard(
        task = Task(
            1, "Testowy tekst, może nawet lorem ipsum", TaskType.ONE_TIME, 123, AlertTrigger.TIME,
            "${LocalTime.now().hour}:${LocalTime.now().minute}", null, "12.03.2023 18:29"
        )
    )
}