package com.palone.planahead.screens.taskEdit.ui.taskTypeSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.screens.taskEdit.TaskEditViewModel
import com.palone.planahead.screens.taskEdit.ui.segmentedButton.SegmentedRadioButton
import com.palone.planahead.screens.taskEdit.ui.taskTypeSelection.typeProperties.TypeProperties

@Composable
fun TaskTypeSection(modifier: Modifier = Modifier, viewModel: TaskEditViewModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier.background(Color.Transparent)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Type")
            SegmentedRadioButton(modifier,
                fields = TaskType.values(),
                selectedField = viewModel.selectedTaskType.collectAsState().value,
                onValueChange = { newTaskType ->
                    viewModel.updateSelectedTaskType(newTaskType)
                })
            TypeProperties(viewModel = viewModel)
        }
    }
}
