package com.palone.planahead.screens.home.ui.components.ChooseOneTimeEventDate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectorItem(onClick: () -> Unit, text: String) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .padding(5.dp)) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = text //TODO add this to string res
        )
    }
}