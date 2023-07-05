package com.palone.planahead.screens.taskEdit.ui.segmentedButton

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T : Enum<T>> SegmentedRadioButtonItem(
    modifier: Modifier = Modifier,
    label: T,
    isChecked: Boolean,
    roundedCornerEnd: Dp,
    roundedCornerStart: Dp,
    onValueChange: (T) -> Unit
) {
    val uncheckedButtonDefaultColor =
        ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    val checkedButtonDefaultColor = ButtonDefaults.buttonColors()
    Button(//Not yet implemented in MaterialDesign 3
        modifier = modifier.height(60.dp),
        colors = if (isChecked) checkedButtonDefaultColor else uncheckedButtonDefaultColor,
        shape = RoundedCornerShape(
            topStart = roundedCornerStart,
            topEnd = roundedCornerEnd,
            bottomStart = roundedCornerStart,
            bottomEnd = roundedCornerEnd
        ), onClick = { onValueChange(label) }) {
        Text(
            text = label.name.replace("_", " "),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 11.sp
        )
    }
}