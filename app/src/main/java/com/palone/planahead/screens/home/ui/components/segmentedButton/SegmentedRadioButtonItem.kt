package com.palone.planahead.screens.home.ui.components.segmentedButton

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun SegmentedRadioButtonItem(
    modifier: Modifier = Modifier,
    label: Enum<*>,
    isChecked: Boolean,
    roundedCornerEnd: Dp,
    roundedCornerStart: Dp,
    onValueChange: (Enum<*>) -> Unit
) {
    val uncheckedButtonDefaultColor =
        ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    val checkedButtonDefaultColor = ButtonDefaults.buttonColors()
    Button(//Not yet implemented in MaterialDesign 3
        modifier = modifier,
        colors = if (isChecked) checkedButtonDefaultColor else uncheckedButtonDefaultColor,
        shape = RoundedCornerShape(
            topStart = roundedCornerStart,
            topEnd = roundedCornerEnd,
            bottomStart = roundedCornerStart,
            bottomEnd = roundedCornerEnd
        ), onClick = { onValueChange(label) }) {
        if (isChecked)
            Icon(imageVector = Icons.Default.Done, contentDescription = "checked")
        Text(text = label.name)
    }
}