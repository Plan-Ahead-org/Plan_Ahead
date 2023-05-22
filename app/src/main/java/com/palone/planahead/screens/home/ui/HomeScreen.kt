package com.palone.planahead.screens.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.palone.planahead.data.task.Task
import com.palone.planahead.data.task.properties.TaskType
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.screens.home.ui.components.AlertTypeSwitch
import com.palone.planahead.screens.home.ui.components.OneTimeOptions
import com.palone.planahead.screens.home.ui.components.TaskCard
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navHostController: NavHostController) {
    Scaffold {
        val tasks = viewModel.allTasks.collectAsState(initial = emptyList())
        val lastExpanded = remember { mutableStateOf(0) }
        val taskRowScrollState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        LaunchedEffect(lastExpanded.value) {//TODO przebudować, zrobione na chwilę
            val id =
                tasks.value.indexOf(tasks.value.find { idToTask -> idToTask.id == lastExpanded.value })
            scope.launch { taskRowScrollState.animateScrollToItem(if (id > 0) id else 0) }
        }
        val mockTask = remember { // TODO tego też się pozbyć jakoś (?)
            mutableStateOf(
                Task(
                    null,
                    "",
                    "",
                    9,
                    "",
                    "${LocalTime.now()}", null, addedDate = LocalDateTime.now().toString()
                )
            )
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(state = taskRowScrollState) {
                items(tasks.value) { task ->
                    TaskCard(
                        task = task,
                        onValueChange = { changedTask ->
                            viewModel.createTask(changedTask) {
                                viewModel.upsertTask(
                                    changedTask
                                )
                            }
                        },
                        onDelete = { taskToDelete -> viewModel.deleteTask(taskToDelete) },
                        onExpanded = { id ->
                            lastExpanded.value = id
                        })
                }
            }

            Button(onClick = {
                //TODO dodać createtask
                viewModel.upsertTask(
                    mockTask.value
                )
            }) {
                Text(text = "Dodaj")
            }
            Card(modifier = Modifier.padding(top = 10.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        Text(text = "Description")
                        TextField(
                            value = mockTask.value.description,
                            onValueChange = {
                                mockTask.value = mockTask.value.copy(description = it)
                            })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "ALARM", fontSize = 9.sp)
                            Checkbox(
                                checked = mockTask.value.alertTypes.toString().contains("1"),
                                onCheckedChange = {
                                    if (it)
                                        mockTask.value =
                                            mockTask.value.copy(alertTypes = (mockTask.value.alertTypes.toString() + "1").toInt())
                                    else
                                        mockTask.value =
                                            mockTask.value.copy(
                                                alertTypes = (mockTask.value.alertTypes.toString()
                                                    .filter { char -> char != '1' }).toInt()
                                            )
                                })
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "NOTIFICATION", fontSize = 9.sp)
                            Checkbox(
                                checked = mockTask.value.alertTypes.toString().contains("2"),
                                onCheckedChange = {
                                    if (it)
                                        mockTask.value =
                                            mockTask.value.copy(alertTypes = (mockTask.value.alertTypes.toString() + "2").toInt())
                                    else
                                        mockTask.value =
                                            mockTask.value.copy(
                                                alertTypes = (mockTask.value.alertTypes.toString()
                                                    .filter { char -> char != '2' }).toInt()
                                            )
                                })
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "PERSISTENT", fontSize = 9.sp)
                            Text(text = "NOTIFICATION", fontSize = 7.sp)
                            Checkbox(
                                checked = mockTask.value.alertTypes.toString().contains("3"),
                                onCheckedChange = {
                                    if (it)
                                        mockTask.value =
                                            mockTask.value.copy(alertTypes = (mockTask.value.alertTypes.toString() + "3").toInt())
                                    else
                                        mockTask.value =
                                            mockTask.value.copy(
                                                alertTypes = (mockTask.value.alertTypes.toString()
                                                    .filter { char -> char != '3' }).toInt()
                                            )
                                })
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Type (task type)")
                        AlertTypeSwitch(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            selected = mockTask.value.taskType,
                            onSwitch = { type ->
                                mockTask.value = mockTask.value.copy(taskType = type)
                            })
                    }
                    if (mockTask.value.taskType == TaskType.ONE_TIME)
                        OneTimeOptions(onSelected = { timeTrigger ->
                            mockTask.value = mockTask.value.copy(alertTimeTrigger = timeTrigger)
                        })


                }
            }
        }
    }
}
