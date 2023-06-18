package com.palone.planahead.screens.taskEdit.ui.expandableList

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> ExpandableList(
    modifier: Modifier = Modifier,
    fields: List<T>,
    selectedField: T,
    onValueChange: (T) -> Unit,
    customEntryContent: @Composable() (ColumnScope.(onValueChange: (T) -> Unit, MutableState<Boolean>) -> Unit) = { _, _ -> }
) {
    val shouldExpand = remember {
        mutableStateOf(false)
    }

    Card(modifier = modifier) {
        if (!shouldExpand.value)
            ExpandableListItem(
                modifier = Modifier.padding(10.dp),
                item = selectedField,
                onClick = {
                    shouldExpand.value = true
                }, suffix = "▼"
            )
        else {
            fields.forEach {
                ExpandableListItem(
                    modifier = Modifier.padding(10.dp),
                    item = it,
                    onClick = { item ->
                        onValueChange(item)
                        shouldExpand.value = false
                    }
                )
            }
            customEntryContent(onValueChange, shouldExpand)
        }
    }
}