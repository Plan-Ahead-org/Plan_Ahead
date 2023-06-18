package com.palone.planahead.screens.taskEdit.ui.expandableList

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> ExpandableListItem(
    modifier: Modifier = Modifier,
    item: T,
    onClick: (T) -> Unit,
    suffix: String = ""
) {
    Text(text = "$item  $suffix", modifier = modifier.clickable { onClick(item) })
}